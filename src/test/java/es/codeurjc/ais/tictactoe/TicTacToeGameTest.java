package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TicTacToeGameTest {

    private TicTacToeGame geim;
    private Connection conecsionUan;
    private Connection conecsionTchu;
    private Player pleierUan;
    private Player pleierTchu;

    @Before
    public void setupTest(){

        geim = new TicTacToeGame();
        conecsionUan = mock(Connection.class);
        conecsionTchu = mock(Connection.class);
        pleierUan = new Player(0, "X", "Paco");
        pleierTchu = new Player(1, "O", "Pepe");
        geim.addConnection(conecsionUan);
        geim.addConnection(conecsionTchu);
        geim.addPlayer(pleierUan);
        geim.addPlayer(pleierTchu);
        verify(conecsionUan,Mockito.times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(geim.getPlayers()));
        verify(conecsionTchu,Mockito.times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(geim.getPlayers()));
        //Estas jugadas son comunes a las tres partidas que simularemos
        ponFicha(conecsionUan, conecsionTchu, pleierUan, geim, 0);
        ponFicha(conecsionUan, conecsionTchu, pleierTchu, geim, 4);
        ponFicha(conecsionUan, conecsionTchu, pleierUan, geim, 1);
        ponFicha(conecsionUan, conecsionTchu, pleierTchu, geim, 2);
        ponFicha(conecsionUan, conecsionTchu, pleierUan, geim, 5);
    }

    @Test
    public void TicTacToeGameTestEmpate() {
        assert !geim.checkTurn(0); //Comprobamos que estmamos en el turno correcto
        assert geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierTchu, geim, 7);
        assert geim.checkTurn(0);
        assert !geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierUan, geim, 6);
        assert !geim.checkTurn(0);
        assert geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierTchu, geim, 3);
        assert geim.checkTurn(0);
        assert !geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierUan, geim, 8);

        ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
        verify(conecsionUan).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        verify(conecsionTchu).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        Object event = argument.getValue();
        WinnerValue winner = (WinnerValue) event;
        Assert.assertNull(winner);
    }

    @Test
    public void TicTacToeGameTestGanar() {

        assert !geim.checkTurn(0); //Comprobamos que estmamos en el turno correcto
        assert geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierTchu, geim, 7);
        assert geim.checkTurn(0);
        assert !geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierUan, geim, 6);
        assert !geim.checkTurn(0);
        assert geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierTchu, geim, 8);
        assert geim.checkTurn(0);
        assert !geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierUan, geim, 3);

        ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
        verify(conecsionUan).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        verify(conecsionTchu).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        Object event = argument.getValue();
        WinnerValue winner = (WinnerValue) event;
        assert winner.player.getId() == 0;
        int [] comparator = {0,3,6};
        Assert.assertArrayEquals(comparator, winner.pos);
    }

    @Test
    public void TicTacToeGameTestPerder() {

        assert !geim.checkTurn(0); //Comprobamos que estmamos en el turno correcto
        assert geim.checkTurn(1);
        ponFicha(conecsionUan, conecsionTchu, pleierTchu, geim, 6);

        ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
        verify(conecsionUan).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        verify(conecsionTchu).sendEvent(eq(EventType.GAME_OVER), argument.capture());
        Object event = argument.getValue();
        WinnerValue winner = (WinnerValue) event;
        assert winner.player.getId() == 1;
        int [] comparator = {6,4,2};
        Assert.assertArrayEquals(comparator, winner.pos);
    }

    private void ponFicha(Connection con1, Connection con2, Player pla, TicTacToeGame game, int pos){
        verify(con1).sendEvent(eq(EventType.SET_TURN), eq(pla));
        verify(con2).sendEvent(eq(EventType.SET_TURN), eq(pla));
        reset(con1);
        reset(con2);
        game.mark(pos);
    }
}
