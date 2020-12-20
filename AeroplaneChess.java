package xyz.chengzi.aeroplanechess;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;
import xyz.chengzi.aeroplanechess.view.View;

import javax.swing.*;

public class AeroplaneChess {
    public static void main(String[] args) {
        new Play0("bj.mp3").start();
        System.setProperty("sun.java2d.win.uiScaleX", "96dpi");
        System.setProperty("sun.java2d.win.uiScaleY", "96dpi");
        View v=new View();
        v.view();
    }
}
