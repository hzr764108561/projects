package xyz.chengzi.aeroplanechess.model;

import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
import xyz.chengzi.aeroplanechess.Play0;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.util.RandomUtil;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.print.attribute.standard.MediaSizeName.C;

public class ChessBoard implements Listenable<ChessBoardListener>{
    private final List<ChessBoardListener> listenerList = new ArrayList<>();
    private final Square[][] grid;
    private final int dimension, endDimension;
    public int first=0;
    public int second=0;
    private ChessBoardLocation saveLocation;
    private int[] winner;

    public int getWinner(int i) {
        return winner[i];
    }
    public void setWinner(int i,int winner){
        this.winner[i]=winner;
    }
    public void restart(){
        controller.setN(1);
        controller.initializeGame();
    }
    public ChessBoard(int dimension, int endDimension) {
        this.grid = new Square[4][dimension + endDimension+5];
        this.dimension = dimension;
        this.endDimension = endDimension;
        winner= new int[]{-1, -1, -1, -1};
        initGrid();
    }
    private ChessBoardComponent chessBoardComponent=new ChessBoardComponent(761,13,6);
    private GameController controller = new GameController(chessBoardComponent,this);
    private GameFrame gameframe=new GameFrame(controller);

    public void setM(int m) {
        this.second = m;
    }

    public void setSaveLocation(ChessBoardLocation saveLocation) {
        this.saveLocation = saveLocation;
    }

    public ChessBoardLocation getSaveLocation() {
        return saveLocation;
    }

    private void initGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension+5; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }
    public void placeInitialPieces() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension+5; j++) {
                grid[i][j].setPiece(null);
            }
        }
        // FIXME: Demo implementation
        grid[0][19].setPiece(new ChessPiece(0,1,1));
        grid[0][20].setPiece(new ChessPiece(0,1,1));
        grid[0][21].setPiece(new ChessPiece(0,2,1));
        grid[0][22].setPiece(new ChessPiece(0,3,1));
        grid[1][19].setPiece(new ChessPiece(1,0,1));
        grid[1][20].setPiece(new ChessPiece(1,1,1));
        grid[1][21].setPiece(new ChessPiece(1,1,1));
        grid[1][22].setPiece(new ChessPiece(1,1,1));
        grid[2][19].setPiece(new ChessPiece(2,0,1));
        grid[2][20].setPiece(new ChessPiece(2,1,1));
        grid[2][21].setPiece(new ChessPiece(2,1,1));
        grid[2][22].setPiece(new ChessPiece(2,1,1));
        grid[3][19].setPiece(new ChessPiece(3,0,1));
        grid[3][20].setPiece(new ChessPiece(3,0,1));
        grid[3][21].setPiece(new ChessPiece(3,0,1));
        grid[3][22].setPiece(new ChessPiece(3,0,1));
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }
    public void loadprocessedGrid(int[][] process){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension + 5; j++) {
                grid[i][j].setPiece(null);
            }
        }
        grid[0][process[0][0]].setPiece(new ChessPiece(0,1,1));
        grid[0][process[0][1]].setPiece(new ChessPiece(0,1,1));
        grid[0][process[0][2]].setPiece(new ChessPiece(0,2,1));
        grid[0][process[0][3]].setPiece(new ChessPiece(0,3,1));
        grid[1][process[1][0]].setPiece(new ChessPiece(1,0,1));
        grid[1][process[1][1]].setPiece(new ChessPiece(1,1,1));
        grid[1][process[1][2]].setPiece(new ChessPiece(1,1,1));
        grid[1][process[1][3]].setPiece(new ChessPiece(1,1,1));
        grid[2][process[2][0]].setPiece(new ChessPiece(2,0,1));
        grid[2][process[2][1]].setPiece(new ChessPiece(2,1,1));
        grid[2][process[2][2]].setPiece(new ChessPiece(2,1,1));
        grid[2][process[2][3]].setPiece(new ChessPiece(2,1,1));
        grid[3][process[3][0]].setPiece(new ChessPiece(3,0,1));
        grid[3][process[3][1]].setPiece(new ChessPiece(3,0,1));
        grid[3][process[3][2]].setPiece(new ChessPiece(3,0,1));
        grid[3][process[3][3]].setPiece(new ChessPiece(3,0,1));
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
        ChessBoardLocation fly = new ChessBoardLocation(currentPlayer, 23);
        if (steps == 13) {
            new Play0("plane_up_1.mp3").start();
            if (getGridAt(fly).getPiece()!=null){
                int n,m;
                n = getGridAt(src).getPiece().getChessNumber();
                m=getGridAt(fly).getPiece().getChessNumber();
                getGridAt(src).getPiece().setChessNumber(n + m);
            }
            setChessPieceAt(fly, removeChessPieceAt(src));
            saveLocation=fly;
        }
        if (steps < 13) {
            ChessBoardLocation dest = src;
            ChessBoardLocation dest1 = dest;
            for (int i = 0; i < steps; i++) {
                if (i == steps - 1 && getGridAt(nextLocation(dest1, currentPlayer)).getPiece() != null) {
                    ChessBoardLocation dest2 = nextLocation(dest1, currentPlayer);
                    if (getGridAt(nextLocation(dest1, currentPlayer)).getPiece().getPlayer() == getGridAt(src).getPiece().getPlayer()) {
                        int n;
                        n = getGridAt(src).getPiece().getChessNumber();
                        getGridAt(src).getPiece().setChessNumber(n + 1);
                    }
                    if (getGridAt(nextLocation(dest1, currentPlayer)).getPiece().getPlayer() != getGridAt(src).getPiece().getPlayer()) {
                        for (int j = 1; j > 0; j++) {
                            controller.fightC(src, dest2, rollDice2(), rollDice3());
                            if (fightWinner()) {
                                ChessBoardLocation region = region(dest2);
                                if (getGridAt(dest2).getPiece().getChessNumber() == 1) {
                                    setChessPieceAt(region, removeChessPieceAt(dest2));
                                    break;
                                }
                                grid[region.getColor()][region.getIndex()].setPiece(new ChessPiece(region.getColor(), region.getIndex(), 1));
                                int n;
                                n = getGridAt(dest2).getPiece().getChessNumber();
                                getGridAt(src).getPiece().setChessNumber(n - 1);
                                continue;
                            }
                            if (!fightWinner()) {
                                if (getGridAt(src).getPiece().getChessNumber() == 1) {
                                    dest = region(src);
                                    break;
                                }
                                ChessBoardLocation region = region(src);
                                grid[region.getColor()][region.getIndex()].setPiece(new ChessPiece(region.getColor(), region.getIndex(), 1));
                                int n;
                                n = getGridAt(src).getPiece().getChessNumber();
                                getGridAt(src).getPiece().setChessNumber(n - 1);
                                continue;
                            }
                        }
                        if (dest.getIndex() != dest1.getIndex()) break;
                    }
                }
                dest1 = nextLocation(dest1, currentPlayer);
                dest = nextLocation(dest, currentPlayer);
                if (dest.getIndex() == 18) {
                    for (int j = 0; j < steps - i - 1; j++) {
                        dest = back(dest, currentPlayer);
                    }
                    break;
                }
            }
            if (dest.getColor() == currentPlayer && dest.getIndex() < 12) {
                if (dest.getColor() == currentPlayer && dest.getIndex() == 4) {
                    new Play0("fly_across_1.mp3").start();
                    dest=new ChessBoardLocation(dest.getColor(),7);
                    ChessBoardLocation path = new ChessBoardLocation((dest.getColor()+2)%4,15);
                    if (getChessPieceAt(path)!=null){
                        for (int i = 0; i < getChessPieceAt(path).getChessNumber(); i++) {
                            if (getChessPieceAt(path).getChessNumber()==1){
                                setChessPieceAt(region(path),getChessPieceAt(path));
                            }
                            else grid[region(path).getColor()][region(path).getIndex()].setPiece(new ChessPiece(region(path).getColor(), region(path).getIndex(), 1));
                        }
                    }
                }
                else dest = new ChessBoardLocation(dest.getColor(), dest.getIndex() + 1);
            }
            if (dest.getColor() == currentPlayer && dest.getIndex() == 4) {
                new Play0("fly_across_1.mp3").start();
                dest=new ChessBoardLocation(dest.getColor(),7);
            }
            setChessPieceAt(dest, removeChessPieceAt(src));
            saveLocation=dest;
            if (dest.getIndex()==18){
                if (home(currentPlayer)!=null){
                    for (int i = 0; i < getChessPieceAt(dest).getChessNumber(); i++) {
                    grid[currentPlayer][home(currentPlayer).getIndex()].setPiece(new ChessPiece(currentPlayer,0,0));
                    listenerList.forEach(listener -> listener.onChessBoardReload(this));}
                    removeChessPieceAt(dest);
                    new Play0("win_fly_back_home_1.mp3").start();
                }
                if (home(currentPlayer)==null){
                    for (int i = 0; i < 4; i++) {
                        ChessBoardLocation finish = new ChessBoardLocation(currentPlayer,i+19);
                        if (getChessPieceAt(finish).getChessNumber()==0&&i+19==22)controller.victory(currentPlayer);
                        if (getChessPieceAt(finish).getChessNumber()!=0)break;
                    }
                }
            }
        }
    }
    public ChessBoardLocation back(ChessBoardLocation location,int currentPlayer) {
        if (location.getIndex()>12) return new ChessBoardLocation(location.getColor(), location.getIndex() -1);
        if (location.getColor() == 0 && location.getIndex() <10) return new ChessBoardLocation(location.getColor() + 3, location.getIndex() + 3);
        if (location.getColor() == 0 && location.getIndex() >= 10)return new ChessBoardLocation(location.getColor() + 3, location.getIndex() -10);
        if (location.getColor() != 0 && location.getIndex() <10)return new ChessBoardLocation(location.getColor() - 1, location.getIndex() +3);
        else{return new ChessBoardLocation(location.getColor() - 1, location.getIndex() -10);}
    }
    public ChessBoardLocation nextLocation(ChessBoardLocation location,int currentPlayer) {
        if (location.getIndex()==23&&location.getColor()==0)return new ChessBoardLocation(location.getColor()+3, location.getIndex() -20);
        if (location.getIndex()==23&&location.getColor()!=0)return new ChessBoardLocation(location.getColor()-1, location.getIndex() -20);
        if (location.getColor() ==currentPlayer&&location.getIndex()>=12)return new ChessBoardLocation(location.getColor(), location.getIndex() +1);
        if (location.getColor() == 3 && location.getIndex() >= 3) return new ChessBoardLocation(location.getColor() - 3, location.getIndex() - 3);
            if (location.getColor() == 3 && location.getIndex() <3)return new ChessBoardLocation(location.getColor() - 3, location.getIndex() +10);
            if (location.getColor() != 3 && location.getIndex() >= 3)return new ChessBoardLocation(location.getColor() + 1, location.getIndex() -3);
            else{return new ChessBoardLocation(location.getColor() + 1, location.getIndex() +10);}


        }
        public boolean fightWinner(){
        if (getM()>getN()){
            return true;
        }
        else return false;
    }
    public ChessBoardLocation region(ChessBoardLocation location){
        for (int i = 0; i < 4; i++) {
            ChessBoardLocation region = new ChessBoardLocation(getGridAt(location).getPiece().getPlayer(), i+19);
            if (getGridAt(region).getPiece()==null) return region;
        }
        return null;
    }
    public ChessBoardLocation home(int player){
        for (int i = 0; i < 4; i++) {
            ChessBoardLocation region = new ChessBoardLocation(player, i+19);
            if (getGridAt(region).getPiece()==null) return region;
        }
        return null;
    }
    public int rollDice2(){
        int n;
            n = RandomUtil.nextInt(1, 6);
        return n;
    }
    public int rollDice3(){
        int m;
            m = RandomUtil.nextInt(1, 6);
        return m;
    }
    public void setN(int n) {
        this.first = n;
    }

    public int getM() {
        return second;
    }

    public int getN() {
        return first;
    }
    public void backToHome(ChessBoardLocation location,ChessBoardLocation location1) {
        if (getGridAt(location).getPiece() != null) {
            int n;
            n= getGridAt(location).getPiece().getChessNumber();
                for (int i = 0; i < getGridAt(location).getPiece().getChessNumber(); i++) {
                    if (n>1) {
                        grid[region(location).getColor()][region(location).getIndex()].setPiece(new ChessPiece(region(location1).getColor(),1,1));
                        listenerList.forEach(listener -> listener.onChessBoardReload(this));
                        n--;
                    }
                    else {
                        grid[region(location).getColor()][region(location).getIndex()].setPiece(new ChessPiece(region(location1).getColor(),1,1));
                        listenerList.forEach(listener -> listener.onChessBoardReload(this));
                        removeChessPieceAt(location);
                        break;}
                }
        }
        if (getGridAt(location1).getPiece() != null) {
            int n;
            n= getGridAt(location1).getPiece().getChessNumber();
            for (int i = 0; i < getGridAt(location1).getPiece().getChessNumber(); i++) {
            if (n>1) {
                grid[region(location1).getColor()][region(location1).getIndex()].setPiece(new ChessPiece(region(location1).getColor(),1,1));
                listenerList.forEach(listener -> listener.onChessBoardReload(this));
                n--;
                }
            else {grid[region(location1).getColor()][region(location1).getIndex()].setPiece(new ChessPiece(region(location1).getColor(),1,1));
                listenerList.forEach(listener -> listener.onChessBoardReload(this));
                removeChessPieceAt(location1);
            break;}
        }
        }
    }
    public boolean chessAtHome(ChessBoardLocation location,int player){
        for (int i = 0; i < 4; i++) {
            ChessBoardLocation region = new ChessBoardLocation(player, i+19);
            if (location.getColor()==region.getColor()&&location.getIndex()==region.getIndex())return true;
        }
        return false;
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
