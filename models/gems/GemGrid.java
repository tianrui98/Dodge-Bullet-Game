package io.github.bbodin.yncgamelab.models.gems;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.bbodin.yncgamelab.models.CellStatus;
import io.github.bbodin.yncgamelab.models.Grid;
import io.github.bbodin.yncgamelab.utils.Assert;
import io.github.bbodin.yncgamelab.utils.Int2;

/**
 * Grid model the grid of Gem.
 * This include basic operations on gems :
 *  - Create: Create a new gem in the grid and return true or false of the gem is correctly created.
 *  - Edit: Change the value of a cell
 *  - Move: Swap with the left or right
 */


public class GemGrid extends Grid {

    private static final String TAG = "GemGrid";

    public GemGrid (int w, int h) {
        super(w,h);
    }

    public boolean Create (Int2 pos, CellStatus status) {
        if (!this.isEditable()) return false;
        Log.d(TAG,"Move(" + pos.getX() + "," +pos.getY() + "," + status.toString() + ")");
        if (this.hasCell(pos)) {
            return false;
        }
        this.setCell(pos, status);
        startGemGridUpdate();
        return true;
    }

    public boolean Change (Int2 pos) {
        if (!this.isEditable()) return false;
        Log.d(TAG,"Change(" + pos.getX() + "," +pos.getY() + ")");
        if (this.hasCell(pos)) {
            CellStatus status = this.getCellStatus(pos);
            Log.d(TAG," Previous status is (" + pos.getX() + "," +pos.getY() + "," + status.toString() + ")");
            CellStatus new_status = CellStatus.values()[(status.ordinal() + 1) % CellStatus.values().length];
            if (new_status == CellStatus.UNKNOWN) {
                new_status = CellStatus.values()[(status.ordinal() + 2) % CellStatus.values().length];
            }
            this.setCell(pos, new_status);
            Log.d(TAG," New status is (" + pos.getX() + "," +pos.getY() + "," + new_status.toString() + ")");
            startGemGridUpdate();
            return true;
        } else {
            Log.d(TAG,"Position not found");
        }
        return false;
    }

    public boolean Move (Int2 pos, Int2 direction) {


        // In this version of the game, we assume only two possible direction, left or right.
        if (!direction.equals(left) && !direction.equals(right)) {
            return false;
        }

        // Move swap me with pos+direction
        Int2 pos1 = pos;
        Int2 pos2 = pos.add(direction);

        CellStatus val1 = this.getCellStatus(pos1);
        CellStatus val2 = this.getCellStatus(pos2);

        if (val1 == CellStatus.UNKNOWN || val2 == CellStatus.UNKNOWN) { // We cannot swap against the wall
            return false;
        }

        this.setCell(pos1,val2);
        this.setCell(pos2,val1);
        this.startGemGridUpdate();
        return true;

    }


    public static GemGrid generateDemo () {
        GemGrid g = new GemGrid (5,7);
        g.Reset();
        return g;
    }

    public void Reset () {

        Assert.isTrue(this.getW() == 5, "Akward test");
        Assert.isTrue(this.getH() == 7, "Akward test");

        this.clear();

        this.setCell(new Int2(0,0), CellStatus.YELLOW);
        this.setCell(new Int2(1,0), CellStatus.GREEN);
        this.setCell(new Int2(2,0), CellStatus.YELLOW);
        this.setCell(new Int2(3,0), CellStatus.YELLOW);
        this.setCell(new Int2(4,0), CellStatus.BLUE);

        this.castChanges();
        this.setCell(new Int2(0,1), CellStatus.YELLOW);
        this.setCell(new Int2(1,1), CellStatus.YELLOW);
        this.setCell(new Int2(2,1), CellStatus.GREEN);
        this.setCell(new Int2(3,1), CellStatus.BLUE);
        this.setCell(new Int2(4,1), CellStatus.YELLOW);

        this.castChanges();
        this.setCell(new Int2(0,2), CellStatus.BLUE);
        this.setCell(new Int2(1,2), CellStatus.ORANGE);
        this.setCell(new Int2(2,2), CellStatus.BLUE);
        this.setCell(new Int2(3,2), CellStatus.BLUE);
        this.setCell(new Int2(4,2), CellStatus.PINK);

        this.castChanges();
        this.setCell(new Int2(0,3), CellStatus.YELLOW);
        this.setCell(new Int2(1,3), CellStatus.ORANGE);
        this.setCell(new Int2(2,3), CellStatus.YELLOW);
        this.setCell(new Int2(3,3), CellStatus.ORANGE);
        this.setCell(new Int2(4,3), CellStatus.ORANGE);

        this.castChanges();

        this.setCell(new Int2(0,4), CellStatus.EMPTY);
        this.setCell(new Int2(1,4), CellStatus.YELLOW);
        this.setCell(new Int2(2,4), CellStatus.ORANGE);
        this.setCell(new Int2(3,4), CellStatus.YELLOW);
        this.setCell(new Int2(4,4), CellStatus.PINK);

        this.castChanges();

        this.setCell(new Int2(0,5), CellStatus.EMPTY);
        this.setCell(new Int2(1,5), CellStatus.EMPTY);
        this.setCell(new Int2(2,5), CellStatus.BLUE);
        this.setCell(new Int2(3,5), CellStatus.EMPTY);
        this.setCell(new Int2(4,5), CellStatus.PINK);

        this.castChanges();
        this.setCell(new Int2(0,6), CellStatus.EMPTY);
        this.setCell(new Int2(1,6), CellStatus.EMPTY);
        this.setCell(new Int2(2,6), CellStatus.GREEN);
        this.setCell(new Int2(3,6), CellStatus.EMPTY);
        this.setCell(new Int2(4,6), CellStatus.BLUE);

        this.castChanges();

    }

    private int updatePhysics() {
        int changed = 0;
        // if down is empty, go down
        for (int x = 0 ; x < this.getW() ; x++) {
            for (int y = 1 ; y < this.getH() ; y++) {
                Int2 pos = new Int2(x,y);
                Int2 below = pos.add(this.down);
                CellStatus current = this.getCellStatus(pos);
                if (this.getCellStatus(below) == CellStatus.EMPTY && this.getCellStatus(pos) != CellStatus.EMPTY ) {
                    this.setCell(pos, CellStatus.EMPTY);
                    this.setCell(below, current);
                    changed++;
                }
            }
        }
        return changed;
    }


    private int breakLines () {

        List<Int2> emptyThem = new ArrayList<>(0);


        // Look for Horizontal line
        Log.d(TAG, "Look for Horizontal lines");
        for (int y = 0 ; y < getH() ; y++) {
            CellStatus previous = CellStatus.UNKNOWN;
            int count          = 0;
            for (int x = 0 ; x <= getW() ; x++) {
                Int2 pos = new Int2(x, y);
                CellStatus current = this.getCellStatus(pos);
                if (current == previous) {
                    count++;
                } else {
                    if (count >= 3 && previous != CellStatus.EMPTY) {
                        // add line
                        Log.d(TAG, "Add horizontal line!");
                        for (int idx = 1 ; idx <= count ; idx++) {
                            emptyThem.add(pos.add(new Int2(-idx, 0)));
                        }
                    }
                    previous = current;
                    count = 1;
                }
            }
        }

        // Look for Vertical line
        Log.d(TAG, "Look for Vertical lines");
        for (int x = 0 ; x < getW() ; x++) {
            CellStatus previous = CellStatus.UNKNOWN;
            int count = 0;
            for (int y = 0; y <= getH(); y++) {
                Int2 pos = new Int2(x, y);
                CellStatus current = this.getCellStatus(pos);
                if (current == previous) {
                    count++;
                } else {
                    if (count >= 3 && previous != CellStatus.EMPTY) {
                        Log.d(TAG, "Add vertical line!");
                        // add line
                        for (int idx = 1; idx <= count; idx++) {
                            emptyThem.add(pos.add(new Int2(0, -idx)));
                        }
                    }
                    previous = current;
                    count = 1;
                }
            }
        }

        // Empty them all
        Log.d(TAG, "Clean " + emptyThem.size() + " cells.");
        for (Int2 pos: emptyThem) {
            this.setCell(pos,CellStatus.EMPTY);
        }

        return (emptyThem.size());
    }


    public void startGemGridUpdate() {

        Log.d(TAG,"startGemGridUpdate() starts");

        castChanges();

        while (this.updatePhysics() > 0) castChanges();

        while (breakLines() > 0) {
            castChanges();
            Log.d(TAG,"Score() = " + breakLines());
            while (this.updatePhysics() > 0) castChanges();
        }

        castChanges();

        Log.d(TAG,"startGemGridUpdate() finished");
    }

}