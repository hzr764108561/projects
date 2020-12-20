package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.InputListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardComponent extends JComponent implements Listenable<InputListener>, ChessBoardListener {
    private static final Color[] BOARD_COLORS = {Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED,Color.pink};
    private static final Color[] PIECE_COLORS = {Color.YELLOW.darker(), Color.BLUE.darker(),
            Color.GREEN.darker(), Color.RED.darker(),Color.PINK.darker()};

    private final List<InputListener> listenerList = new ArrayList<>();
    private final SquareComponent[][] gridComponents;
    private final int dimension, endDimension;
    private final int gridSize;

    public ChessBoardComponent(int size, int dimension, int endDimension) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLayout(null); // Use absolute layout
        setSize(size, size);
        setLocation(0,30);

        this.gridComponents = new SquareComponent[4][dimension + endDimension + 5];
        this.dimension = dimension;
        this.endDimension = endDimension;
        this.gridSize = size / (dimension + 2);
        initGridComponents();
    }

    private int gridLocation(int player, int index) {
        int boardIndex = (1 + 13 * player + 4 * index)% (4 * dimension);
        int boardIndex1 = (1 + 13 * player + 4 * index);
        int x, y;
        if (boardIndex >= 7 && boardIndex <= 13) {
            x = (boardIndex - 3) * gridSize;
            y = 0;
        } else if (boardIndex >= 4 && boardIndex <= 6) {
            x = gridSize * 4;
            y = (boardIndex - 3) * gridSize;
        } else if (boardIndex < 4 && boardIndex >= 0 && boardIndex1 < 50 && boardIndex1 != 1) {
            x = (boardIndex ) * gridSize;
            y = 5 * gridSize;
        } else if(boardIndex < 17 && boardIndex >= 14){
            x = 10 * gridSize;
            y = (boardIndex - 13) * gridSize;
        } else if(boardIndex < 21 && boardIndex >= 17){
            x = (boardIndex - 6) * gridSize;
            y = 4 * gridSize;
        } else if(boardIndex < 27 && boardIndex >= 21){
            x = 14 * gridSize;
            y = (boardIndex - 16) * gridSize;
        } else if(boardIndex < 30 && boardIndex >= 27){
            x = (40 - boardIndex) * gridSize;
            y = 10 * gridSize;
        } else if(boardIndex < 33 && boardIndex >= 30){
            x = 10 * gridSize;
            y = (boardIndex - 19) * gridSize;
        } else if(boardIndex < 40 && boardIndex >= 33){
            x = (43 - boardIndex ) * gridSize;
            y = 14 * gridSize;
        } else if(boardIndex < 43 && boardIndex >= 40){
            x = 4 * gridSize;
            y = (53 - boardIndex) * gridSize;
        } else if(boardIndex < 46 && boardIndex >= 43){
            x = (46 - boardIndex) * gridSize;
            y = 10 * gridSize;
        } else if(boardIndex < 52 && boardIndex >= 46){
            x = 0;
            y = (56 - boardIndex) * gridSize;
        } else if((boardIndex1 < 56 && boardIndex1 >= 52) || boardIndex1 == 1 ){
            if (boardIndex1 == 1){x = gridSize; }
            else {x = (boardIndex1 - 52) * gridSize; }
            y = 4* gridSize;
        }else {x = 0 ; y = 0;}

        return x << 16 | y;
    }

    private int endGridLocation(int player, int index) {
        int beforeEndGridLocation = gridLocation(player, dimension - 1);
        int x = beforeEndGridLocation >> 16, y = beforeEndGridLocation & 0xffff;
        if (y == 0) {
            y += (index + 1) * gridSize;
        } else if (x == 0) {
            x += (index + 1) * gridSize;
        } else if (y == 14  * gridSize) {
            y -= (index + 1) * gridSize;
        } else {
            x -= (index + 1) * gridSize;
        }
        return x << 16 | y;
    }

    private int waitGridLocation(int player, int index){
        int x,y;
        if (player == 0 && index > 18 ){
            if (index < 21){ x = 0; y = (index - 19) * gridSize; }
            else if (index < 23){ x = gridSize;y = (index - 21) * gridSize; }
            else {x = 0;y = 2 * gridSize + 7;}
        } else if (player == 1 && index > 18 ){
            if (index < 21){ x = 14 * gridSize; y = (index - 19) * gridSize; }
            else if (index < 23){ x = 13 * gridSize;y = (index - 21) * gridSize; }
            else {x = 12 * gridSize - 7;y = 0;}
        } else if (player == 3 && index > 18 ){
            if (index < 21){ x = 0; y = (index - 6) * gridSize; }
            else if (index < 23){ x = gridSize;y = (index - 8) * gridSize; }
            else {x = 2 * gridSize + 7;y = 14 * gridSize ;}
        } else {
            if (index < 21){ x = 14 * gridSize; y = (index - 6) * gridSize; }
            else if (index < 23){ x = 13 * gridSize;y = (index - 8) * gridSize; }
            else {x = 14 * gridSize;y = 12 * gridSize - 7;}
        }
        return x << 16 | y;
    }


    private void initGridComponents() {
        for (int player = 0; player < 4; player++) {
            for (int index = 0; index < dimension; index++) {
                int gridLocation = gridLocation(player, index);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                add(gridComponents[player][index]);
            }
            for (int index = dimension; index < dimension + endDimension; index++) {
                int gridLocation = endGridLocation(player, index - dimension);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                add(gridComponents[player][index]);
            }

            for (int index = dimension + endDimension; index < dimension + endDimension + 5; index++) {
                int gridLocation = waitGridLocation(player, index);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                add(gridComponents[player][index]);
            }


        }
    }

    public SquareComponent getGridAt(ChessBoardLocation location) {
        return gridComponents[location.getColor()][location.getIndex()];
    }

    public void setChessAtGrid(ChessBoardLocation location, Color color) {
        removeChessAtGrid(location);
        getGridAt(location).add(new ChessComponent(color));
    }

    public void removeChessAtGrid(ChessBoardLocation location) {
        // Note: re-validation is required after remove / removeAll
        getGridAt(location).removeAll();
        getGridAt(location).revalidate();
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent instanceof SquareComponent) {
                SquareComponent square = (SquareComponent) clickedComponent;
                ChessBoardLocation location = new ChessBoardLocation(square.getPlayer(), square.getIndex());
                for (InputListener listener : listenerList) {
                    if (clickedComponent.getComponentCount() == 0) {
                        listener.onPlayerClickSquare(location, square);
                    } else {
                        listener.onPlayerClickChessPiece(location, (ChessComponent) square.getComponent(0));
                    }
                }
            }
        }
    }

    @Override
    public void onChessPiecePlace(ChessBoardLocation location, ChessPiece piece) {
        setChessAtGrid(location, PIECE_COLORS[piece.getPlayer()]);
        repaint();
    }

    @Override
    public void onChessPieceRemove(ChessBoardLocation location) {
        removeChessAtGrid(location);
        repaint();
    }

    @Override
    public void onChessBoardReload(ChessBoard board) {
        for (int color = 0; color < 4; color++) {
            for (int index = 0; index < board.getDimension()+11; index++) {
                ChessBoardLocation location = new ChessBoardLocation(color, index);
                ChessPiece piece = board.getChessPieceAt(location);
                if (piece != null) {
                    setChessAtGrid(location, PIECE_COLORS[piece.getPlayer()]);
                } else {
                    removeChessAtGrid(location);
                }
            }
        }
        repaint();
    }

    @Override
    public void registerListener(InputListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(InputListener listener) {
        listenerList.remove(listener);
    }
}
