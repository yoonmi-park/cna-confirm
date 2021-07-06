package userone;

import userone.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired ConfirmRepository confirmRepository;
    //추가 
    ////ConfirmRepository confirmRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookingCreated_ConfirmRequest(@Payload BookingCreated bookingCreated){

        if(bookingCreated.isMe()){
            

            Confirm confirm = new Confirm();
            confirm.setStatus("BOOKED");
            confirm.setUserId(bookingCreated.getBookingUserId());
            //confirm.setConfirmDtm(bookingCreated.getUseStartDtm());
           // confirm.set
            // 추가 event
            confirm.setBookingId(bookingCreated.getId());

            confirmRepository.save(confirm);
            System.out.println("##### listener ConfirmRequest : " + bookingCreated.toJson());
        }
    }

}
