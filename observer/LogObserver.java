package observer;

public class LogObserver implements Observer {
    @Override
    public void update(String mensagem) {
        System.out.println("[LOG] " + mensagem);
    }
}
