package chessGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardData {

	private String moves;
	private boolean whiteSide = true;	// true is down, false is up
	private boolean turn = true;	// true is white, false is black
	private int[][] pieces;			// 1 = pawn
									// 2 = rook
									// 3 = knight
									// 4 = bishop
									// 5 = king
									// 6 = queen
	
	private static final int AI_SEARCH_DEPTH = 3; 
	
	public BoardData() {
		moves = "";
		pieces = new int[ChessPanel.SQUARE_COUNT][ChessPanel.SQUARE_COUNT];
		if(!whiteSide) {
			for(int i = 0; i < pieces.length; i++) {
				for(int j = 0; j < pieces.length; j++) {
					if(j == 1 || j == ChessPanel.SQUARE_COUNT - 2) {
						pieces[i][j] = 1;
					} else if(j == 0 || j == ChessPanel.SQUARE_COUNT - 1) {
						pieces[i][j] = i+2 < 6 ? i+2 : 10-i;
					} else {
						pieces[i][j] = 0;
					}
				}
			}
		} else {
			for(int i = pieces.length-1; i >= 0; i--) {
				for(int j = pieces.length-1; j >= 0; j--) {
					if(j == 1 || j == ChessPanel.SQUARE_COUNT - 2) {
						pieces[i][j] = 1;
					} else if(j == 0 || j == ChessPanel.SQUARE_COUNT - 1) {
						pieces[i][j] = i+2 < 5 ? i+2 : 9-i;
					} else {
						pieces[i][j] = 0;
					}
				}
			}
		}
	}
	
	public String getMoves(){
		return moves;
	}
	
	public void setMoves(String moves) {
		this.moves = moves;
	}
	
	public void paintPieces(Graphics g, int squareSize, int xOffset, int yOffset) {
		g.setColor(Color.GRAY);
		for(int i = 0; i < ChessPanel.SQUARE_COUNT; i++) {
			for(int j = 0; j < ChessPanel.SQUARE_COUNT; j++) {
				g.drawString(Integer.toString(pieces[i][j]), i*squareSize + xOffset + squareSize/2, j*squareSize + yOffset + squareSize/2);
			}
		}
	}
}
