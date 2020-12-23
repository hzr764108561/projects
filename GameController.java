package xyz.chengzi.aeroplanechess.controller;

import xyz.chengzi.aeroplanechess.listener.GameStateListener;
import xyz.chengzi.aeroplanechess.listener.InputListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;
import xyz.chengzi.aeroplanechess.util.RandomUtil;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.ChessComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;
import xyz.chengzi.aeroplanechess.view.SquareComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameController implements InputListener, Listenable<GameStateListener> {
    private final List<GameStateListener> listenerList = new ArrayList<>();
    private final ChessBoardComponent view;
    private final ChessBoard model;
    private Integer steps;
    private Integer rolledNumber;
    private Integer rolledNumber1;
    private int currentPlayer;
    private int n=1;
    private ChessBoardLocation Location;
    private ChessBoardLocation chessBoardLocation1;
    private ChessBoardLocation chessBoardLocation2;


    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard) {
        this.view = chessBoardComponent;
        this.model = chessBoard;
        view.registerListener(this);
        model.registerListener(view);
    }
    public void setN(int n){
       this.n=n;
    }

    public int getN() {
        return n;
    }

    public void setSteps(int n ){
        this.steps=n;
    }
    public ChessBoardComponent getView() {
        return view;
    }

    public ChessBoard getModel() {
        return model;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void initializeGame() {
        model.placeInitialPieces();
        rolledNumber = null;
        rolledNumber1=null;
        currentPlayer = 0;
        listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
    }
    public void LoadGame(ArrayList<int[]> process) {
        model.loadprocessedGrid(process);
        rolledNumber = null;
        rolledNumber1=null;
        currentPlayer = 0;
        listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
    }

    public int rollDice() {
        if (rolledNumber == null) {
            return rolledNumber = RandomUtil.nextInt(1, 6);
        } else {
            return -1;
        }
    }

    public void setRolledNumber(Integer rolledNumber) {
        this.rolledNumber = rolledNumber;
    }

    public void setRolledNumber1(Integer rolledNumber1) {
        this.rolledNumber1 = rolledNumber1;
    }

    public int rollDice1() {
        if (rolledNumber1 == null) {
            return rolledNumber1 = RandomUtil.nextInt(1, 6);
        } else {
            return -1;
        }
    }
    public int nextPlayer() {
        steps=null;
        rolledNumber = null;
        rolledNumber1=null;
        currentPlayer = (currentPlayer + 1) % 4;
        return currentPlayer;
    }
    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        System.out.println("clicked " + location.getColor() + "," + location.getIndex());
    }
    public void setLocation(ChessBoardLocation Location){
        this.Location=Location;
    }
    public ChessBoardLocation getLocation(){
        return Location;
    }
    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        if (steps != null) {
            ChessPiece piece = model.getChessPieceAt(location);
            if (piece.getPlayer() == currentPlayer) {
                if (rolledNumber + rolledNumber1 >= 10) {
                    if (steps == 13) {
                        if (!model.chessAtHome(location, currentPlayer)) {
                            listenerList.forEach(listener -> listener.onPlayerStartRound(13));
                        }
                        if (rolledNumber1 != null && n == 3) {
                            model.backToHome(chessBoardLocation1, chessBoardLocation2);
                            nextPlayer();
                            listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
                        }
                        if (model.chessAtHome(location, currentPlayer)) {
                            if (rolledNumber1 != null && n == 1) {
                                model.moveChessPiece(location, steps, (currentPlayer));
                                listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                                currentPlayer = (currentPlayer + 3) % 4;
                                chessBoardLocation1 =model.getSaveLocation();
                                nextPlayer();
                                listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer + 4));
                                n++;
                            }
                            if (rolledNumber1 != null && n == 2) {
                                model.moveChessPiece(location, steps, (currentPlayer));
                                listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                                currentPlayer = (currentPlayer + 3) % 4;
                                chessBoardLocation2 =model.getSaveLocation();
                                nextPlayer();
                                listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer + 4));
                                n++;
                            }
                        }
                    }
                    if (steps != null && steps != 13) {
                        if (model.home(currentPlayer) != null) {
                            if (model.chessAtHome(location, currentPlayer)) {
                                listenerList.forEach(listener -> listener.onPlayerStartRound(12));
                            }
                            if (!model.chessAtHome(location, currentPlayer)) {
                                if (rolledNumber1 != null && n == 3) {
                                    model.backToHome(chessBoardLocation1, chessBoardLocation2);
                                    nextPlayer();
                                    listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
                                }
                                if (rolledNumber1 != null && n == 1) {
                                    model.moveChessPiece(location, steps, (currentPlayer));
                                    listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                                    currentPlayer = (currentPlayer + 3) % 4;
                                    chessBoardLocation1 =model.getSaveLocation();
                                    nextPlayer();
                                    listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer + 4));
                                    n++;
                                }
                                if (rolledNumber1 != null && n == 2) {
                                    model.moveChessPiece(location, steps, (currentPlayer));
                                    listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                                    currentPlayer = (currentPlayer + 3) % 4;
                                    chessBoardLocation2 =model.getSaveLocation();
                                    nextPlayer();
                                    listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer + 4));
                                    n++;
                                }
                            }
                        }
                    }
                }
                if (rolledNumber1 != null && rolledNumber + rolledNumber1 < 10) {
                    n = 1;
                    if (steps == 13) {
                        if (!model.chessAtHome(location, currentPlayer)) {
                            listenerList.forEach(listener -> listener.onPlayerStartRound(13));
                        }
                        if (model.chessAtHome(location, currentPlayer)) {
                            model.moveChessPiece(location, steps, currentPlayer);
                            steps = null;
                            listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                            nextPlayer();
                            listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
                        }
                    }
                    if (steps != null && steps != 13) {
                        if (model.home(currentPlayer) != null) {
                            if (model.chessAtHome(location, currentPlayer)) {
                                listenerList.forEach(listener -> listener.onPlayerStartRound(12));
                            }
                            if (!model.chessAtHome(location, currentPlayer)) {
                                model.moveChessPiece(location, steps, currentPlayer);
                                listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                                nextPlayer();
                                listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
                            }
                        }
                    }
                }
            }
        }
        }

    public void fightC(ChessBoardLocation src,ChessBoardLocation dest2,int m,int n){
        listenerList.forEach(listener -> listener.fight(src,dest2,m,n));
    }
    public boolean chessNotFly(){
        ChessBoardLocation region0 = new ChessBoardLocation(0,0);
        ChessBoardLocation region1 = new ChessBoardLocation(0,1);
        ChessBoardLocation region2 = new ChessBoardLocation(0,2);
        ChessBoardLocation region3 = new ChessBoardLocation(0,3);
        if (model.getChessPieceAt(region0)!=null&&model.getChessPieceAt(region1)!=null&&model.getChessPieceAt(region2)!=null&&model.getChessPieceAt(region3)!=null){
            return true;
        }
        return false;
    }
    @Override
    public void registerListener(GameStateListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameStateListener listener) {
        listenerList.remove(listener);
    }
}
