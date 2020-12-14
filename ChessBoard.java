package xyz.chengzi.aeroplanechess.model;

import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.print.attribute.standard.MediaSizeName.C;

public class ChessBoard implements Listenable<ChessBoardListener> {
    private final List<ChessBoardListener> listenerList = new ArrayList<>();
    private final Square[][] grid;
    private final int dimension, endDimension;
    private final ChessBoardComponent view;
    private GameController controller;
    public int player2;
    public int m=0;

    public ChessBoard(int dimension, int endDimension) {
        this.grid = new Square[4][dimension + endDimension];
        this.dimension = dimension;
        this.endDimension = endDimension;
        initGrid();
        view = null;
        controller=null;
    }
    public int getPlayer2(){
        return player2;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }

    private void initGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }
    public void placeInitialPieces() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension; j++) {
                grid[i][j].setPiece(null);
            }
        }
        // FIXME: Demo implementation
        grid[0][0].setPiece(new ChessPiece(0,0,1));
        grid[0][1].setPiece(new ChessPiece(0,1,1));
        grid[0][2].setPiece(new ChessPiece(0,2,1));
        grid[0][3].setPiece(new ChessPiece(0,3,1));
        grid[1][0].setPiece(new ChessPiece(1,0,1));
        grid[1][10].setPiece(new ChessPiece(1,1,1));
        grid[2][0].setPiece(new ChessPiece(2,0,1));
        grid[1][2].setPiece(new ChessPiece(1,1,1));
        grid[2][1].setPiece(new ChessPiece(2,1,1));
        grid[3][0].setPiece(new ChessPiece(3,0,1));
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }
    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getColor()][location.getIndex()];
    }

    public int getDimension() {
        return dimension;
    }

    public int getEndDimension() {
        return endDimension;
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }
    public void moveChessPiece(ChessBoardLocation src, int steps,int currentPlayer) {
        ChessBoardLocation dest = src;
        ChessBoardLocation dest1 =dest;
        for (int i = 0; i < steps; i++) {
            if (i == steps - 1 &&getGridAt(nextLocation(dest1, currentPlayer)).getPiece()!=null) {
                ChessBoardLocation dest2=nextLocation(dest1, currentPlayer);
                if (getGridAt(nextLocation(dest1, currentPlayer)).getPiece().getPlayer()==getGridAt(src).getPiece().getPlayer()){
                    int n;
                    n=getGridAt(src).getPiece().getChessNumber();
                    getGridAt(src).getPiece().setChessNumber(n+1);
                }
                if (getGridAt(nextLocation(dest1, currentPlayer)).getPiece().getPlayer()!=getGridAt(src).getPiece().getPlayer()){
                    for (int j = 1; j >0; j++) {
                        if (fightWinner()) {
                            ChessBoardLocation region = region(dest2);
                            if (getGridAt(dest2).getPiece().getChessNumber()==1){
                                setChessPieceAt(region, removeChessPieceAt(dest2));
                                break;
                            }
                            grid[region.getColor()][region.getIndex()].setPiece(new ChessPiece(region.getColor(),region.getIndex(),1));
                            int n;
                            n=getGridAt(dest2).getPiece().getChessNumber();
                            getGridAt(src).getPiece().setChessNumber(n-1);
                            continue;
                        }
                        if (!fightWinner()) {
                            if (getGridAt(src).getPiece().getChessNumber()==1){
                                dest=region(src);
                                break;
                            }
                            ChessBoardLocation region = region(src);
                            grid[region.getColor()][region.getIndex()].setPiece(new ChessPiece(region.getColor(),region.getIndex(),1));
                            int n;
                            n=getGridAt(src).getPiece().getChessNumber();
                            getGridAt(src).getPiece().setChessNumber(n-1);
                            continue;
                        }
                    }
                    if (dest!=src)break;
                }
            }
            dest1 = nextLocation(dest1,currentPlayer);
            dest = nextLocation(dest,currentPlayer);
            if (getGridAt(nextLocation(dest1, currentPlayer)).getPiece() != null&&getGridAt(src).getPiece().getChessNumber()<getGridAt(nextLocation(dest1, currentPlayer)).getPiece().getChessNumber()){
                for (int j = 0; j <steps-i-1; j++) {
                    dest=back(dest,currentPlayer);
                }
                break;
            }
            if (dest.getIndex()==18){
                for (int j = 0; j <steps-i-1; j++) {
                    dest=back(dest,currentPlayer);
                }
                break;
            }
        }
        if (dest.getColor() ==currentPlayer&&dest.getIndex()<12&&dest.getIndex()>4){
            dest=new ChessBoardLocation(dest.getColor(),dest.getIndex()+1);
        }
        setChessPieceAt(dest, removeChessPieceAt(src));
    }
    public ChessBoardLocation back(ChessBoardLocation location,int currentPlayer) {
        if (location.getIndex()>12) return new ChessBoardLocation(location.getColor(), location.getIndex() -1);
        if (location.getColor() == 0 && location.getIndex() <10) return new ChessBoardLocation(location.getColor() + 3, location.getIndex() + 3);
        if (location.getColor() == 0 && location.getIndex() >= 10)return new ChessBoardLocation(location.getColor() + 3, location.getIndex() -10);
        if (location.getColor() != 0 && location.getIndex() <10)return new ChessBoardLocation(location.getColor() - 1, location.getIndex() +3);
        else{return new ChessBoardLocation(location.getColor() - 1, location.getIndex() -10);}
    }
    public ChessBoardLocation nextLocation(ChessBoardLocation location,int currentPlayer) {
        if (location.getColor() ==currentPlayer&&location.getIndex()>=12)return new ChessBoardLocation(location.getColor(), location.getIndex() +1);
        if (location.getColor() == 3 && location.getIndex() >= 3) return new ChessBoardLocation(location.getColor() - 3, location.getIndex() - 3);
            if (location.getColor() == 3 && location.getIndex() <3)return new ChessBoardLocation(location.getColor() - 3, location.getIndex() +10);
            if (location.getColor() != 3 && location.getIndex() >= 3)return new ChessBoardLocation(location.getColor() + 1, location.getIndex() -3);
            else{return new ChessBoardLocation(location.getColor() + 1, location.getIndex() +10);}


        }
        public boolean fightWinner(){
        return false;
    }
    public ChessBoardLocation region(ChessBoardLocation location){
        for (int i = 0; i < 4; i++) {
            ChessBoardLocation region = new ChessBoardLocation(getGridAt(location).getPiece().getPlayer(), i);
            if (getGridAt(region).getPiece()==null) return region;
        }
        return null;
    }
    @Override
    public void registerListener(ChessBoardListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(ChessBoardListener listener) {
        listenerList.remove(listener);
    }
}
