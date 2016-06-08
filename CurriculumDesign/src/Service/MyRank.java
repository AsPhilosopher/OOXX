package Service;

import java.awt.Color;
import java.awt.Font;
import java.awt.color.ColorSpace;

import javax.swing.JLabel;

public class MyRank {

	private JLabel ranking, player, sum, win, lose, dogfall, winrate;
	private Font font = new Font("楷体", Font.BOLD, 15);
	private final int wordHeight = 30; //字高
	
	private final int 
	ranking_x = 10, player_x = 60, sum_x = 160, win_x = 255, lose_x = 320, dogfall_x = 385, winrate_x = 450;

	private final int rank_width = 30, player_width = 80, else_width = 50;
	
	
	public MyRank() {
		ranking = new JLabel();
		player = new JLabel();
		sum = new JLabel();
		win = new JLabel();
		lose = new JLabel();
		dogfall = new JLabel();
		winrate = new JLabel();
	}
	
	public JLabel getRanking() {
		return ranking;
	}



	public JLabel getPlayer() {
		return player;
	}



	public JLabel getSum() {
		return sum;
	}



	public JLabel getWin() {
		return win;
	}



	public JLabel getLose() {
		return lose;
	}



	public JLabel getDogfall() {
		return dogfall;
	}



	public JLabel getWinrate() {
		return winrate;
	}



	public void setRanking(int y, String text) {
		
//		ranking = new JLabel(text);
		ranking.setText(text);
		ranking.setBounds(ranking_x, y, rank_width, wordHeight);
		ranking.setFont(font);
		ranking.setForeground(Color.BLACK);
	}



	public void setPlayer(int y, String text) {
		
//		player = new JLabel(text);
		player.setText(text);
		player.setBounds(player_x, y, player_width, wordHeight);
		player.setFont(font);
		player.setForeground(Color.BLACK);
//		return player;
	}



	public void setSum(int y, String text) {
//		sum = new JLabel(text);
		sum.setText(text);
		sum.setBounds(sum_x, y, else_width, wordHeight);
		sum.setFont(font);
		sum.setForeground(Color.BLACK);
//		return sum;
	}



	public void setWin(int y, String text) {
//		win = new JLabel(text);
		win.setText(text);
		win.setBounds(win_x, y, else_width, wordHeight);
		win.setFont(font);
		win.setForeground(Color.BLACK);
//		return win;
	}



	public void setLose(int y, String text) {
		
//		lose = new JLabel(text);
		lose.setText(text);
		lose.setBounds(lose_x, y, else_width, wordHeight);
		lose.setFont(font);
		lose.setForeground(Color.BLACK);
//		return lose;
	}



	public void setDogfall(int y, String text) {
		
//		dogfall = new JLabel(text);
		dogfall.setText(text);
		dogfall.setBounds(dogfall_x, y, else_width, wordHeight);
		dogfall.setFont(font);
		dogfall.setForeground(Color.BLACK);
//		return dogfall;
	}


	public void setWinrate(int y, String text) {
		
//		winrate = new JLabel(text);
		winrate.setText(text);
		winrate.setBounds(winrate_x, y, else_width, wordHeight);
		winrate.setFont(font);
		winrate.setForeground(Color.BLACK);
//		return winrate;
	}
	
	public void makeChange(int flag) {
		
		Color color;
		
		if(flag == 0) {
			color = Color.RED;
		}else if(flag == 1) {
//			color = new Color(255, 215, 0);
			color = new Color(255, 255, 0);
		}else if(flag == 2) {
			color = new Color(192, 192, 192);
		}else{
//			color = new Color(218, 165, 32);
			color = new Color(255, 215, 0);
		}
		
		ranking.setForeground(color);
		player.setForeground(color);
		sum.setForeground(color);
		win.setForeground(color);
		lose.setForeground(color);
		dogfall.setForeground(color);
		winrate.setForeground(color);
		
	}

	public void clearLabel() {
		dogfall.setText("");
		player.setText("");
		sum.setText("");
		win.setText("");
		lose.setText("");
		ranking.setText("");
		winrate.setText("");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
