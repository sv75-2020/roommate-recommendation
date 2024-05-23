import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.MonthlyPaymentEvent;
import com.ftn.sbnz.model.models.Payment;
import com.ftn.sbnz.model.models.Reservation;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.repository.MonthlyPaymentRepository;
import com.ftn.sbnz.model.repository.PaymentRepository;
import com.ftn.sbnz.model.repository.ReservationRepository;

@Service
public class BillingService {
    @Autowired
    public PaymentRepository paymentRepository;
    
    @Autowired 
    public ReservationRepository reservationRepository;

    @Autowired
    public MonthlyPaymentRepository monthlyPaymentRepository;

    public void addBills(){
        List<Reservation> reservations = reservationRepository.findAll();
        for(Reservation r : reservations){
            Payment p=new Payment();
            p.setPaidRoommate1(false);
            p.setPaidRoommate2(false);
            LocalDate dateAfterSevenDays = LocalDate.now().plusDays(7);
            p.setPaymentDue(dateAfterSevenDays);
            p.setReservation(r);
            paymentRepository.save(p);

            MonthlyPaymentEvent mp1=new MonthlyPaymentEvent();
            mp1.setPaymentDate(LocalDate.now());
            mp1.setUser(r.getRoommates().getRoommate1());
            monthlyPaymentRepository.save(mp1);

            MonthlyPaymentEvent mp2=new MonthlyPaymentEvent();
            mp2.setPaymentDate(LocalDate.now());
            mp2.setUser(r.getRoommates().getRoommate2());
            monthlyPaymentRepository.save(mp2);

        }
    }

    public void checkPayments(){
        for(Reservation r : reservationRepository.findAll()){

        }
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleMonthlyBilling() {
        addBills();
    }

}
