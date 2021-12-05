package graphics;

public abstract class NonDefaultPoppable implements Poppable {

    @Override
    public void initializePopUp() {
        System.out.println(getClass().getSimpleName().toUpperCase() + " POPUP CANNOT BE LOADED WITH DEFAULT LOAD METHOD");
    }

}
