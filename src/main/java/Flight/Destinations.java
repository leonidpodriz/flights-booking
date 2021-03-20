package Flight;

public enum Destinations {
    LONDON,
    BEIJING,
    ANKARA,
    VENICE,
    ATHENS,
    BERLIN,
    SYDNEY,
    TOKYO,
    NEWYORK,
    CHICAGO,
    CANADA,
    PRAGUE,
    WARSAW,
    CAIRO,
    DRESDEN,
    ROME,
    PARIS;

    public static final Destinations[] dest = Destinations.values();

    public static Destinations getRandomDest(){
        int index = (int) (Math.random() * dest.length);
        return dest[index];
    }

}
