package observer;

public class IndexadorObserver implements Observer {
    @Override
    public void update(String mensagem) {
        System.out.println("[INDEXADOR] Indexando -> " + mensagem);
    }
}
