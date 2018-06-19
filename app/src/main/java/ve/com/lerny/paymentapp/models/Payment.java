package ve.com.lerny.paymentapp.models;

public class Payment {

    public Double amount;
    public MethodPaymentResponse method;
    public BankResponse bank;
    public InstallmentsCostsResponse installment;
    public Boolean finish = false;

    public void reset() {
        amount = null;
        method = null;
        bank = null;
        installment = null;
        finish = false;
    }
}
