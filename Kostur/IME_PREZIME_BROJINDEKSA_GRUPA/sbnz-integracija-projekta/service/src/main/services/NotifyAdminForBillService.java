import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.sbnz.model.repository.NotifyAdminForBillRepository;

@Service
public class NotifyAdminForBillService {
    @Autowired
    public NotifyAdminForBillRepository notifyAdminForBillRepository;

    public List<NotifyAdminForBill> getNotifications(){
        return notifyAdminForBillRepository.findAll();
    }
}
