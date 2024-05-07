// Felipe Reis Corerato 14569800
class A {
    private int m;
    private int n;

    public A(int mIn, int nIn) {
        m = mIn;
        n = nIn;
    }

    public void m1() {
        m = m + n;
    }

    // Getter for 'm'
    public int getM() {
        return m;
    }

    // Setter for 'm'
    public void setM(int m) {
        this.m = m;
    }

    // Getter for 'n'
    public int getN() {
        return n;
    }

    // Setter for 'n'
    public void setN(int n) {
        this.n = n;
    }

    // Override toString method to represent object as a string
    @Override
    public String toString() {
        return "A = (" + this.m + ", " + this.n + ")";
    }
}

public class B extends A {
    // Constructor
    public B(int mIn, int nIn) {
        super(mIn, nIn);
    }

    // Override m1 method to modify 'm' differently
    @Override
    public void m1() {
        // Modifies 'm' by subtracting 'n' instead of adding
        this.setM(getM() - getN());
    }

    // Override toString method to represent object as a string
    @Override
    public String toString() {
        return "B = (" + this.getM() + ", " + this.getN() + ")";
    }

    public static void main(String[] args) {
        A a = new A(1, 2);
        A b = new B(1, 2);
        System.out.println(a + " " + b);
        a.m1();
        b.m1();
        System.out.println(a + " " + b);
    }
}
