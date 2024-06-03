package com.backend.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "meetings")
@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;//자동 아이디

    @Column(nullable=false, unique=true)
    private String meetingName;

    @ManyToOne(optional=false)
    @JoinColumn(name = "user_id")
    private User user;//주최자정보

    // @OneToMany(mappedBy = "meeting", cascade = CascadeType.REMOVE, orphanRemoval = true)
    // private List<Participant> participants = new ArrayList<>();



    public void patch(Meeting meeting) {
        if(meeting.meetingName != null){
            this.meetingName= meeting.meetingName;
        }
        
    }

    // public void patch(String meetingName) {
    //     if(meetingName != null){
    //         this.meetingName= meetingName;
    //     }
        
    // }
}
