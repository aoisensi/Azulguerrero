package info.aoisensi.azulguerrero;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;


public class AzulguerreroChestLock {
	private static String ChestLockKeyword = "[チェストロック]";
	
	public static boolean TryOpenChest(PlayerInteractEvent e){
		Player player = e.getPlayer();
		String owner = GetChestOwner((Chest)e.getClickedBlock().getState());
		PlayerInventory playerinventory = player.getInventory();
		//看板を持ってるかどうか
		if(playerinventory.getItemInHand().getType() == Material.SIGN) {
			if(null != owner){
				if(player.getName().equals(owner)){
					return false;
				}else{
					player.sendMessage(ChatColor.RED + "このチェストは" + owner + "によってロックされてます");
					return true;
				}
			}else{
				Location clickLoc = BlockFace(e.getClickedBlock().getLocation(), e.getBlockFace());
				//下から貼り付けようとしてないか確かめる
				if(e.getBlockFace() != BlockFace.DOWN){
					//看板を貼ろうとしたところになにかブロックがないか確認する
					if(Material.AIR == clickLoc.getBlock().getType()){
						//クリエイティブでなければ看板をひとつ減らす
						if(player.getGameMode() != GameMode.CREATIVE){
							playerinventory.getItemInHand().setAmount(playerinventory.getItemInHand().getAmount() - 1);
						}
						Block clickBlock = clickLoc.getBlock();
						Material matSign = ProperSign(e.getBlockFace());
						clickBlock.setType(matSign);
						Sign sign = (Sign)clickBlock.getState();
						//看板内容書き込み
						sign.setLine(0, ChestLockKeyword);
						String[] message = SignArray(player.getName());
						for(int i=0;i<3;++i){
							sign.setLine(i + 1, message[i]);
						}
						//書き込みここまで
						org.bukkit.material.Sign s = new org.bukkit.material.Sign(matSign);
						s.setFacingDirection(e.getBlockFace());//向き設定
						sign.setData(s);
						sign.update();
						return true;
					}else{
						player.sendMessage(ChatColor.RED + "ブロックがあるので鍵をかけられません");
						return true;
					}
				}else{
					return false;//開けさせる
				}
			}
		}else{
			if(null != owner){
				if(player.getName().equals(owner)){
					return false;
				}else{
					player.sendMessage(ChatColor.RED + "このチェストは" + owner + "によってロックされてます");
					return true;
				}
			}else{
				return false;
			}
		}
	}
	
	public static boolean TryBreakSign(BlockBreakEvent e){
		Player player = e.getPlayer();
		String owner = CheckLockSign(e.getBlock());
		if(null == owner){
			return false;
		}else if(player.getName().equals(owner)){
			return false;
		}else{
			player.sendMessage(ChatColor.RED + "このチェストは" + owner + "によってロックされてます");
			return true;
		}
	}
	
	public static boolean TryBreakChest(BlockBreakEvent e){
		Player player = e.getPlayer();
		String owner = GetChestOwner((Chest)e.getBlock().getState());
		if(null == owner){
			return false;
		}else if(player.getName().equals(owner)){
			return false;
		}else{
			player.sendMessage(ChatColor.RED + "このチェストは" + owner + "によってロックされてます");
			return true;
		}
	}
	
	/**
	 * chestがラージチェストならもうひとつのチェストを
	 * 違うならnullを返す
	 * @param chest
	 * @return
	 */
	private static Chest CheckLargeChest(Chest chest){
		Location loc = chest.getLocation();
		loc.setX(loc.getX() + 1);
		if(IsChest(loc)){
			return (Chest)loc.getBlock().getState();
		}
		
		loc = chest.getLocation();
		loc.setX(loc.getX() - 1);
		if(IsChest(loc)){
			return (Chest)loc.getBlock().getState();
		}
		
		loc = chest.getLocation();
		loc.setZ(loc.getZ() + 1);
		if(IsChest(loc)){
			return (Chest)loc.getBlock().getState();
		}
		
		loc = chest.getLocation();
		loc.setZ(loc.getZ() - 1);
		if(IsChest(loc)){
			return (Chest)loc.getBlock().getState();
		}
		return null;
	}

	/**
	 * locがチェストならtrueを返す
	 * @param loc
	 * @return
	 */
	private static boolean IsChest(Location loc){
		return loc.getBlock().getType() == Material.CHEST;
	}

	/**
	 * ブロックの場所と、面を指定してそれに隣接するブロックの場所を返す
	 */
	private static Location BlockFace(Location loc, BlockFace face){
		loc.setX(loc.getX() + face.getModX());
		loc.setY(loc.getY() + face.getModY());
		loc.setZ(loc.getZ() + face.getModZ());
		return loc;
	}
	
	/**
	 * ブロックの面に対する適切な看板を返す
	 * @param face
	 * @return
	 */
	private static Material ProperSign(BlockFace face){
		if(face == BlockFace.UP){
			return Material.SIGN_POST;
		}else{
			return Material.WALL_SIGN;
		}
	}
	
	/**
	 * ブロックが看板かどうか調べ、
	 * さらにその看板がロック専用看板かどうか
	 * それだった場合、プレイヤーを
	 * 違う場合nullを返す
	 */
	private static String CheckLockSign(Block block){
		Material mat = block.getType();
		if(mat == Material.WALL_SIGN || mat == Material.SIGN_POST){
			Sign sign = (Sign)block.getState();
			if(sign.getLine(0).equals(ChestLockKeyword)){
				return sign.getLine(1) + sign.getLine(2) + sign.getLine(3);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * チェストのオーナーを調べる　ない場合はnull
	 * @param chest
	 * @return
	 */
	private static String GetChestOwner(Chest chest){
		String owner;
		if(null != (owner = StackSignCheck(chest.getBlock()))){
			return owner;
		}else{
			Chest largeChest;
			if(null != (largeChest = CheckLargeChest(chest))){
				if(null != (owner = StackSignCheck(largeChest.getBlock()))){
					return owner;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
	}
	
	/**
	 * ついているロック看板がないか調べ、あった場合はオーナープレイヤーを返す
	 * @param block
	 * @return
	 */
	private static String StackSignCheck(Block block){
		String owner = null;
		if(null != (owner = CheckSignStack(block, BlockFace.UP))){
			return owner;
		}else if(null != (owner = CheckSignStack(block, BlockFace.EAST))){
			return owner;
		}else if(null != (owner = CheckSignStack(block, BlockFace.NORTH))){
			return owner;
		}else if(null != (owner = CheckSignStack(block, BlockFace.SOUTH))){
			return owner;
		}else if(null != (owner = CheckSignStack(block, BlockFace.WEST))){
			return owner;
		}else{
			return owner;
		}
	}
	
	/**
	 * ブロックと方向を指定して、ブロックにブロックの方向にある看板が張り付いてるか調べ、その看板がロックチェストだった場合オーナーを返す
	 * それ以外ならnullを返す
	 */
	private static String CheckSignStack(Block block, BlockFace blockface){
		Location loc = BlockFace(block.getLocation(), blockface);
		Block blk = loc.getBlock();
		if(blk.getType() == Material.WALL_SIGN || blk.getType() == Material.SIGN_POST){
			org.bukkit.material.Sign sign = (org.bukkit.material.Sign)blk.getState().getData();
			if(StackCheck(blockface, sign.getAttachedFace())){
				return CheckLockSign(loc.getBlock());
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	private static boolean StackCheck(BlockFace block, BlockFace sign){
		switch(block){
		case UP:
			return true;
		case WEST:
			return sign == BlockFace.EAST;
		case EAST:
			return sign == BlockFace.WEST;
		case SOUTH:
			return sign == BlockFace.NORTH;
		case NORTH:
			return sign == BlockFace.SOUTH;
		default:
			return false;
		}
	}
	
	private static String[] SignArray(String message){
		String result[] = new String[3];
		for(int i=0;i<3;++i){
			try{
				result[i] = message.substring(i * 15, (i + 1) * 15);
			}catch(StringIndexOutOfBoundsException e0){
				try{
					result[i] = message.substring(i * 15);
				}catch(StringIndexOutOfBoundsException e1){
					//nop 何もしない
				}
			}
		}
		return result;
	}
	
}
