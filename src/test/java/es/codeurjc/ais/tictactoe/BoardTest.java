package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Cell;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BoardTest {

    Board bard;
    int[] winner;
    @Before
    public void setupTest(){
        bard = new Board();
    }

    @Test
    public void winHorizontalTest() {
        for (int i = 0; i < 9; i++) {
            if (i == 0){
                ponFicha("x", 0);
            }
            else if (i == 3) {
                ponFicha("o", 3);
            }
            else if (i == 1) {
                ponFicha("x", 1);
            }
            else if (i == 4) {
                ponFicha("o", 4);
            }
            else if (i == 2) {
                ponFicha("x", 2);
            }
        }
        winner = bard.getCellsIfWinner("x");
        int[] comparator = {0, 1, 2};
        assert !bard.checkDraw();  //Trivial
        Assert.assertArrayEquals(winner, comparator);
    }

    @Test
    public void winDiagonalTest() {
        for (int i = 0; i < 9; i++) {
            if (i == 0){
                ponFicha("x", 0);
            }
            else if (i == 3) {
                ponFicha("o", 3);
            }
            else if (i == 4) {
                ponFicha("x", 4);
            }
            else if (i == 5) {
                ponFicha("o", 5);
            }
            else if (i == 8) {
                ponFicha("x", 8);
            }
        }
        winner = bard.getCellsIfWinner("x");
        int[] comparator = {0, 4, 8};
        assert !bard.checkDraw();  //Trivial
        Assert.assertArrayEquals(winner, comparator);
    }

    @Test
    public void winVerticalTest() {
        for (int i = 0; i < 9; i++) {
            if (i == 0){
                ponFicha("x", 0);
            }
            else if (i == 2) {
                ponFicha("o", 2);
            }
            else if (i == 3) {
                ponFicha("x", 3);
            }
            else if (i == 1) {
                ponFicha("o", 1);
            }
            else if (i == 6) {
                ponFicha("x", 6);
            }
        }
        winner = bard.getCellsIfWinner("x");
        int[] comparator = {0, 3, 6};
        assert !bard.checkDraw();  //Trivial
        Assert.assertArrayEquals(winner, comparator);
    }

    @Test
    public void drawTest() {
        for (int i = 0; i < 9; i++) {
            if (i == 0){
                ponFicha("x", 0);
            }
            else if (i == 1) {
                ponFicha("x", 1);
            }
            else if (i == 2) {
                ponFicha("o", 2);
            }
            else if (i == 3) {
                ponFicha("o", 3);
            }
            else if (i == 4) {
                ponFicha("o", 4);
            }
            else if (i == 5) {
                ponFicha("x", 5);
            }
            else if (i == 6) {
                ponFicha("x", 6);
            }
            else if (i == 7) {
                ponFicha("o", 7);
            }
            else {
                ponFicha("x", 8);
            }
        }
        // Si es null, nadie gana
        int[] firstDrawer = bard.getCellsIfWinner("x");
        Assert.assertNull(firstDrawer);
        int[] secondDrawer = bard.getCellsIfWinner("o");
        Assert.assertNull(secondDrawer);
        assert bard.checkDraw(); //Si todas estan llenas, es que hay empate
    }

    private void ponFicha(String label, int pos){
        Cell celula = bard.getCell(pos);
        celula.value = label;
    }
}