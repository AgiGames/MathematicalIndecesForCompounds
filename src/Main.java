class Main{
    public static void main(String[] args) {
        Compound compound = new Compound("1,2-chloro-ethane");
        System.out.println(compound);
        compound.getTopologicalIndeces();
    }
};