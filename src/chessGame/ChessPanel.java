package chessGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import internalData.TabItem;

public class ChessPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int SQUARE_COUNT = 8;
	private BoardData data;
	
	
	public ChessPanel() {
		data = new BoardData();
	}
	
	public String getMoveSet() {
		return data.getMoves();
	}

	public void setMoves(String moves) {
		data.setMoves(moves);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int yOffset = getHeight() / 10;
		int height = getHeight() - (2*yOffset);
		
		int size = width < height ? width : height;
		size = ((int)size / SQUARE_COUNT) * SQUARE_COUNT;
		boolean side = width - (2*yOffset) < height;
		int xOffset = size < width ? (width-size)/4 : 0;
		yOffset = width < height ? (getHeight() - size) / 2 : yOffset;
		
		boolean currentColor = true;
		int squareSize = size / SQUARE_COUNT;
		for(int i = 0; i < SQUARE_COUNT; i++) {
			for(int j = 0; j < SQUARE_COUNT; j++) {
				
				if(currentColor) {
					g.setColor(Color.white);
				} else {
					g.setColor(Color.black);
				}
				
				g.fillRect(i*squareSize + xOffset, j*squareSize + yOffset, squareSize, squareSize);
				
				currentColor = !currentColor;
			}
			currentColor = !currentColor;
		}
		g.setColor(TabItem.themeAccent);
		g.drawRect(xOffset, yOffset, size, size);
		
		data.paintPieces(g, squareSize, xOffset, yOffset);
		
		
	}
}
