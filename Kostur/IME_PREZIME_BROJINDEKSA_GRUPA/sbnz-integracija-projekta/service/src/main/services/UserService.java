import java.time.LocalDate;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.sbnz.model.events.BillPaidEvent;
import com.ftn.sbnz.model.repository.BillPaidRepository;
import com.ftn.sbnz.model.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BillPaidRepository billPaidRepository;

    public payBill(LocalDate date){
        BillPaidEvent bp=new BillPaidEvent();
        LocalDate currDate = LocalDate.now();
        bp.setPaymentDate(currDate);
        bp.setUser(userRepository.findOne(1L));
        billPaidRepository.save(bp);
    }
}
