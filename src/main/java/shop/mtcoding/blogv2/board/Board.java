package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = true)
    private String content;

    // fetch는 프로젝션!
    @JsonIgnore // 제이슨으로 줄때는 이 유저 데이터는 안주겠다.
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1 + N

    
    // ManyToone Eager 전략 (디폴트)
    // OneTomany Lazy 전략 (디폴트)
    // OneToMany -> 양방향 맵핑
    // "board" -> 변수명
    // 연관관계의 주인을 설정
    // board, user는 orm 하지마!
    @JsonIgnoreProperties({"board"})// 상위의 board가 아니라 리플리 안에있는 보더를 말하는거임
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY) // 나는 폴인키가 아니에요 !!
    // @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 나는 폴인키가 아니에요 !!
    private List<Reply> replies = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

}