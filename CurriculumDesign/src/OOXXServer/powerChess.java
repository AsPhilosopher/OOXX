package OOXXServer;
import java.awt.Color;

import OOXXGUI.ChessBoard;


public class powerChess {

	//0代表空 1代表白棋 2代表黑棋
	private static final int ROWS = ChessBoard.ROWS;
	private static final int CLOS = ChessBoard.COLS;
	private char[][] situation  = new char[ROWS + 1][CLOS + 1];
	private ChessBoard chessBoard;
	
	public powerChess(ChessBoard chessBoard) {
		
		for(int i=1; i<=ROWS; i++)
			for(int j=1; j<=CLOS; j++)
				situation[i][j] = '0';
		this.chessBoard = chessBoard;
	}
	//测试方法
	public void printSituation() {
		for(int i=1; i<=ROWS; i++) {
			for(int j=1; j<=CLOS; j++) {
				System.out.print(situation[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("\n\n");	
	}
	//点下落方法
	public boolean PointsFall() {
		//先把所有"空点"变'0'
		for(int y = 1; y<=ROWS; y++) {

			for(int x=1; x<=CLOS; x++) {
				if(situation[y][x] == 'O' || situation[y][x] == 'X')
					situation[y][x] = '0';
			}
		}
		boolean ans = false;
		char tc;
		for(int y = 2; y<=ROWS; y++) {
			for(int x = 1; x<=CLOS; x++) {
				if(situation[y][x] != '0' && situation[y-1][x] == '0') {
					ans = true;
					tc = situation[y][x];
					situation[y][x] = situation[y-1][x];
					situation[y-1][x] = tc;
				}
			}
		}
//		Refresh();
		return ans;
	}
	//判断是否有子
	public boolean getisHas(int x, int y) {
		if(x>CLOS || y > ROWS) return true;
		return (situation[y][x] != '0');
	}
	
	public void setValue(int x, int y, char c) {
		situation[y][x] = c;
	}
	
	public char getValue(int x,int y) {
		return situation[x][y];
	}
	
	public char getChar(int x,int y) {
		return situation[y][x];
	}
	
	public boolean isOut(int x,int y) {
		if(x<1 || x>CLOS || y<1 || y>ROWS)
			return true;
		return false;
	}
	
	public int DoFour(int x, int y, int dx, int dy,char c1, char c2) {
		
		int tx = x;
		int ty = y;
		int count = 0;
		while(true) {
			tx += dx;
			ty += dy;
			if(isOut(tx, ty) || (situation[tx][ty] != c1 && situation[tx][ty] != c2)) break;
			
			if(situation[tx][ty] == c1 || situation[tx][ty] == c2) {
				++count;
			}else {
				break;
			}
		}
		
		tx = x;
		ty = y;
		
		while(true) {
			tx -= dx;
			ty -= dy;
			if(isOut(tx, ty) || (situation[tx][ty] != c1 && situation[tx][ty] != c2)) break;
			
			if(situation[tx][ty] == c1 || situation[tx][ty] == c2) {
				++count;
			}else {
				break;
			}
		}
		
		tx = x;
		ty = y;
		
		if(count >= 3) {
//			flag =  true;
			count = 0;
			situation[x][y] = c2;
			while(true) {
				tx += dx;
				ty += dy;
				if(isOut(tx, ty))
					break;
				if(situation[tx][ty] == c1) {
					situation[tx][ty] = c2;
					++count;
				}else if(situation[tx][ty] != c2)
					break;
			}
			
			tx = x;
			ty = y;
			
			while(true) {
				tx -= dx;
				ty -= dy;
				if(isOut(tx, ty))
					break;
				if(situation[tx][ty] == c1) {
					situation[tx][ty] = c2;
					++count;
				}else if(situation[tx][ty] != c2)
					break;
			}
		}else {
			count = 0;
		}
			
		return count;
	}
	
	//是否连成四个
	public int  isFour(int x,int y) {
//		System.out.println("&&");
		int count = 0;
		int t=x;x=y;y=t;
//		boolean flag = false;
		char c1,c2;
		if(situation[x][y] == '1') { //白色
			c1 = '1';
			c2 = 'O';
		}else {  //黑色
			c1 = '2';
			c2 = 'X';
		}
		
		count += DoFour(x, y, 1, 0, c1, c2);
		
		count += DoFour(x, y, 1, 1, c1, c2);
		
		count += DoFour(x, y, 0, 1, c1, c2);
		
		count += DoFour(x, y, 1, -1, c1, c2);
		
		if(count != 0) ++count;
		return count;
	}
	
	//判断一个子最终会落在哪儿 即返回最后的y坐标
	public int End(int x, int y) {
		if(y == 1)
			return 1;
		
		int endy, i;
		for(i = y-1; i>=1; i--) {
			if(situation[i][x] != '0')
				break;
		}

		endy = i+1;
		return endy;
	}
	
	public boolean doAllMap(int power,char cc) {
		boolean myans = false;
		int t = 0;
		for(int i=1; i<=ROWS; i++) {
			for(int j=1; j<=CLOS; j++) {
				if( !myans &&  (situation[j][i] == '1' ||  situation[j][i] == '2') && isFour(i, j) > 0) {
					myans = true;
//					char c = situation[j][i];
					
					/*if(c == cc && (cc == 'O' || cc == '1')) {
//						ChessBoard.whitescore += t*power;
						System.out.println(t + " " + power + "白" + chessBoard.getWhitescore());/////////
						chessBoard.setWhitescore(chessBoard.getWhitescore() +  t*power);
					}
					
					else if(c == cc && (cc == 'X' || cc == '2')) {
						System.out.println(t  + " " + power + "黑" + chessBoard.getBlackscore());/////////
						chessBoard.setBlackscore(chessBoard.getBlackscore() +  t*power);
					}*/
				}
			} 
		}
		if(myans && cc == 'O') {//白色
			for(int i=1; i<=ROWS; i++) {
				for(int j=1; j<=CLOS; j++) {
					if(situation[j][i] == 'O') {
						++t;
					}
					
					System.out.print(situation[i][j] + " ");//////////
				}
				System.out.println();//////
			}
			chessBoard.setWhitescore(chessBoard.getWhitescore() +  t*power);
			System.out.println(t + " " + power + "白" + chessBoard.getWhitescore());/////////
		}
		
		
		else if(myans && cc == 'X') { //黑
			for(int i=1; i<=ROWS; i++) {
				for(int j=1; j<=CLOS; j++) {
					if(situation[j][i] == 'X') {
						++t;
					}
					
					System.out.print(situation[i][j] + " ");//////////
				}
				
				System.out.println();/////;
			}
			chessBoard.setBlackscore(chessBoard.getBlackscore() +  t*power);
			System.out.println(t + " " + power + "黑" + chessBoard.getBlackscore());/////////
		}
		
		
//		Refresh();
		return myans;
	}
	
	public void Restart() {
		for(int i=1; i<=ROWS; i++)
			for(int j=1; j<=CLOS; j++)
				situation[i][j] = '0';
//		Refresh();
	}
	
	public boolean isFull() {
		for(int i=1; i<=ROWS; i++) {
			for(int j=1; j<=CLOS; j++) {
				if(situation[i][j] == '0')
					return false;
			}
		}
		return true;
	}
	
	//1代表白胜  2代表黑胜 0代表平局 -1代表未分胜负
	public int getResult() {
		if(chessBoard.getBlackscore() >= ChessBoard.TARGETSCORE && 
				chessBoard.getWhitescore() >= ChessBoard.TARGETSCORE) {
			if(chessBoard.getBlackscore() > chessBoard.getWhitescore()) {
				return 2;
			}
			if(chessBoard.getBlackscore() < chessBoard.getWhitescore()) {
				return 1;
			}
			return 0;
		}
		
		if(chessBoard.getBlackscore() >= ChessBoard.TARGETSCORE)
			return 2;
		if(chessBoard.getWhitescore() >= ChessBoard.TARGETSCORE)
			return 1;
		return -1;
	}
	
	public int MyRJ() {
//		System.out.println("***");
		int x_ans = 0;
		int arr[] = new int[this.CLOS + 1];//记录每列是否能下
		long power[] = new long[this.CLOS + 1];//记录每列的价值
		long maxPower = 0;
		
		for(int i=1;i<=this.CLOS; i++) {
			for(int j=1;j<=this.ROWS;j++) {
				power[i] = 0;
				if(situation[j][i] == '0') {
						
					arr[i] = j;
					break;
					
				}else {
					arr[i] = 0;
				}
			}
		}
		
		//人机对战算法
		for(int i=1;i<=this.CLOS; i++) {
			if(arr[i] != 0) { //System.out.println("@@");
				power[i] = RJAlgorithm(power[i], i, arr[i] , 1, 0); //arr[i]表示行
				power[i] = RJAlgorithm(power[i], i, arr[i] , 0, 1); //arr[i]表示行
				power[i] = RJAlgorithm(power[i], i, arr[i] , 1, 1); //arr[i]表示行
				power[i] = RJAlgorithm(power[i], i, arr[i] , -1, 1); //arr[i]表示行
			}
		}
		///测试部分
		for(int i=1;i<=this.CLOS; i++) {
			System.out.printf("%d  ",power[i]);
		}System.out.println();
		
		for(int i=1; i<=this.CLOS; i++) {
			if(power[i] > maxPower) {
				maxPower = power[i];
				x_ans = i;
			}
		}
		
		if(maxPower == 0) { //如果最小为0
			x_ans = this.CLOS/2 - 2 + (int)(Math.random()*4 + 1);
//			System.out.println(x_ans);
			
			if(arr[x_ans] == 0 || reTest(arr[x_ans]+1, x_ans)) {//该位置已满 或 特殊情况
				System.out.println("@@##");
				int i;
				for(i=1;i<=this.CLOS; i++)
					if(arr[i] != 0 && !reTest(arr[i]+1, i)) {
						x_ans = i;
						break;
					}
				//保证有落子
				if(i == (CLOS + 1)) {
					for(i=1;i<=this.CLOS; i++) {
						if(arr[i] != 0) {
							x_ans = i;
							break;
						}
					}
				}
			}
		}
		
		return x_ans;
	}
	
	public long RJAlgorithm(long nowpower, int x,int y, int dx, int dy) { //y表示行
		long ans = nowpower;
		int tx = x, ty = y;
		int cou1 = 0, cou2 = 0;
		boolean flagThrough1 = false, flagThrough2 = false;

		while(true) {
			tx += dx;
			ty += dy;
			if(isOut(tx, ty)) {
				break;
			}
			
			if(cou1 == 0) {
				if(situation[ty][tx] == '1')
					++cou1;
				else if(situation[ty][tx] == '2')
					--cou1;
				else{
//					flagThrough = true;
					break;
				}
			}
			else {
				if(cou1 < 0 && situation[ty][tx] == '1')
					break;
				if(cou1 > 0 && situation[ty][tx] == '2')
					break;
				if(situation[ty][tx] == '1')
					++cou1;
				else if(situation[ty][tx] == '2') {
					--cou1;
				}
			}
			
			if(situation[ty][tx] == '0' && (ty == 1 || situation[ty-1][tx] != '0')) {
				flagThrough1 = true;
				break;
			}
		}
		
		ans = helpRJ(ans, cou1, flagThrough1, x, y+1); //////
		
		tx = x; ty = y;
		
		while(true) {
			tx -= dx;
			ty -= dy;
			if(isOut(tx, ty)) {
				break;
			}
			
			if(cou2 == 0) {
				if(situation[ty][tx] == '1')
					++cou2;
				else if(situation[ty][tx] == '2')
					--cou2;
				else{
//					flagThrough = true;
					break;
				}
			}
			else {
				if(cou2 < 0 && situation[ty][tx] == '1')
					break;
				if(cou2 > 0 && situation[ty][tx] == '2')
					break;
				if(situation[ty][tx] == '1')
					++cou2;
				else if(situation[ty][tx] == '2') {
					--cou2;
				}
			}
			
			if(situation[ty][tx] == '0' && (ty == 1 || situation[ty-1][tx] != '0')) {
				flagThrough2 = true;
				break;
			}
		}
		
		ans = helpRJ(ans, cou2, flagThrough2, x, y+1);
		
		if(cou1*cou2 > 0) {
			if(flagThrough2 || flagThrough1)
				ans = helpRJ(ans, cou1+cou2, true, x, y+1);
			else
				ans = helpRJ(ans, cou1+cou2, false, x, y+1);
		}
		
		return ans;
	}
	
	public long helpRJ(long nowpower, int cou, boolean through ,int x,int y) {
		long ans = nowpower;
		long temp;
		if(cou > 0) {
			if(cou >= 3) {

				temp = 1;
				temp <<= 60;
//				System.out.println(temp);///////
				if( (ans & temp ) == 0 ) {
					ans += temp;
				}
				
//				System.out.println(temp + "@@" + ans);
			}
			else if(cou == 2) {
				
				System.out.println(y + " " +x + " 22" );/////////////
				
				if(reTest(x, y)) {
					System.out.println(true + " 22 " + ans + " " + y + " " +x);
					return ans;
//					return 0;
				}
				
				if(through) {

					temp = 1;
					temp <<= 30;
//					System.out.println(temp);///////
					if( (ans & temp ) == 0 ) {
						ans += temp;
					}
				}
				else {

					temp = 1;
					temp <<= 10;
//					System.out.println(temp);///////
					if( (ans & temp ) == 0 ) {
						ans += temp;
					}
				}
				
			}
			
		}else if(cou < 0) {
			if(cou <= -3) {
   
				temp = 1;
				temp <<= 50;
//				System.out.println(temp);///////
				if( (ans & temp ) == 0 ) {
					ans += temp;
				}
			}
			else if(cou == -2) {
				System.out.println(y + " " +x + "---2" );///////
				
				if(reTest(x, y)) {
					System.out.println(true + " ---22 " + ans + " " + y + " " +x);
//					return 0;
					return ans;
				}
				
				if(through) { 
  
					temp = 1;
					temp <<= 40;
//				    System.out.println(temp);///////
					if( (ans & temp ) == 0 ) {
						ans += temp;
					}
				}
				else {

					temp = 1;
					temp <<= 20;
					if( (ans & temp ) == 0 ) {
					ans += temp;
					}
				}
			}
		}
		
		return ans;
	}
	
	public boolean reTest(int x,int y) {
//		int tx = x, ty= y ;
//		int t;
//		t = x;x = y; y = t;
		
		int cou = 0;
//		boolean flag;
		if(y > ROWS)
			return false;
		//横
		for(int i=1; i<CLOS; i++) {
			if(isOut(x + i, y) || situation[y][x+i] != '2')
				break;
			else ++cou;
		}for(int i=1; i<CLOS; i++) {
			if(isOut(x - i, y) || situation[y][x-i] != '2')
				break;
			else ++cou;
		}
		if(cou >= 3)
			return true;
		
		cou = 0;
		//撇
		for(int i=1; i<CLOS; i++) {
			if(isOut(x + i, y + i) || situation[y + i][x + i] != '2')
				break;
			else ++cou;
		}for(int i=1; i<CLOS; i++) {
			if(isOut(x - i, y - i) || situation[y - i][x - i] != '2')
				break;
			else ++cou;
		}
		if(cou >= 3)
			return true;
		
		cou = 0;
		//捺
		for(int i=1; i<CLOS; i++) {
			if(isOut(x - i, y + i) || situation[y + i][x - i] != '2')
				break;
			else ++cou;
		}for(int i=1; i<CLOS; i++) {
			if(isOut(x + i, y - i) || situation[y - i][x + i] != '2')
				break;
			else ++cou;
		}
		if(cou >= 3)
			return true;
		
		return false;
		
//		return flag;
	}

}
