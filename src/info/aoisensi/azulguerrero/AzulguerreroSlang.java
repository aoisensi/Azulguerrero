package info.aoisensi.azulguerrero;

public class AzulguerreroSlang {
	public enum Message{
		HI("hi"),
		WTF("wtf"),
		GG("gg"),
		BENRI("benri"),
		BYE("bye"),
		COOL("cool"),
		THX("thx"),
		GJ("gj"),
		PLZ("plz"),
		NOP("");
		
		private final String name;

		private Message(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		public static Message toMessage(String name) {

			Message result = null;

	       for (Message animal : values()) {
	        	if (animal.toString().equals(name)) {
	        		result = animal;
	        		break;
	            }
	        }
        
       return result != null ? result : NOP;
    	}
	}
	public static String Replace(String message){
		switch(Message.toMessage(message)){
		case HI:
			return "こんにちは！";
		case WTF:
			return "なんてこったい／(^o^)＼";
		case GG:
			return "いい試合だ";
		case BENRI:
			return "便利";
		case BYE:
			return "バイバイ(^^)/~~~";
		case COOL:
			return "かっこいいね！";
		case THX:
			return "ありがと！";
		case GJ:
			return "よくやった！";
		case PLZ:
			return "おねがいします";
		default:
			return message;
		}
	}
}
