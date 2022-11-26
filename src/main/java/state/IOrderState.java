package state;

public interface IOrderState {

    void previousStatus();

    void nextStatus();

    void issueOrder();

    void finishOrder();

}
