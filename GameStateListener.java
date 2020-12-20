package xyz.chengzi.aeroplanechess.listener;

import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;

public interface GameStateListener extends Listener {
    void onPlayerStartRound(int player);

    void onPlayerEndRound(int player);
    void fight(ChessBoardLocation src,ChessBoardLocation dest2,int m,int n);
}
