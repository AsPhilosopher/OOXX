package OOXXServer;

public class MyTool {

	private int x_now, y_now;
	
	public void getNowXY(String getmes) {
		int index = getmes.indexOf("@");
		System.out.println(index);
		System.out.println(getmes.length());
		x_now = Integer.parseInt(getmes.substring(0, index));
		y_now = Integer.parseInt(getmes.substring(index+1, getmes.length()));
	}
	
	public void printXY() {
		System.out.println(x_now + " " + y_now);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTool myTool = new MyTool();
		myTool.getNowXY("44@76");
		
		myTool.printXY();
	}

}
