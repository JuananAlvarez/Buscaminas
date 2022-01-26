import javax.swing.JButton;

public class Casillas extends JButton {
	private int casillaXH;
    private int casillaYV;
    private boolean clickIzq;
    private boolean clickDrc;
    private boolean mina;
 

    public Casillas() {
        casillaXH = 0;
        casillaYV = 0;
        clickIzq = false;
        clickDrc = false;
        mina = false;
    }

    public Casillas(int xH, int yV, boolean clickIzq, boolean clickDrc, boolean mina) {
        this.casillaXH = xH;
        this.casillaYV = yV;
        this.clickIzq = clickIzq;
        this.clickDrc = clickDrc;
        this.mina = mina;
    }


    public int getCasillaXH() {
		return casillaXH;
	}

	public void setCasillaXH(int casillaXH) {
		this.casillaXH = casillaXH;
	}

	public int getCasillaYV() {
		return casillaYV;
	}

	public void setCasillaYV(int casillaYV) {
		this.casillaYV = casillaYV;
	}

	public boolean isClickIzq() {
        return clickIzq;
    }

    public void setClickIzq(boolean clickIzq) {
        this.clickIzq = clickIzq;
    }

    public boolean isClickDrc() {
        return clickDrc;
    }

    public void setClickDrc(boolean clickDrc) {
        this.clickDrc = clickDrc;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }


}
