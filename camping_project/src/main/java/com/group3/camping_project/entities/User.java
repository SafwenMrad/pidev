package com.group3.camping_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group3.camping_project.entities.enums.Gender;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String firstName ;
    private String lastName;
    private String email;


    private String password;
    private double phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    //@Enumerated(value = EnumType.STRING)
    //private Role role;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @OneToOne
    private Image profileImage;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @ManyToMany
    private List<CampingSpace> createdCampingSpace;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Equipment> equipment;

    @ManyToMany(mappedBy = "goingUsers")
    private List<GroupCamping> groupCamping;

    @OneToMany(mappedBy = "owner")
    private List<GroupCamping> createdGroupCamping  ;


    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Chatroom>chatroomCreated;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Chatroom>chatrooms;


    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Comment> comments;


    @OneToMany(mappedBy = "author")
    private List<Review> campingSpaceReview;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<Message>messages;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<LikeDislikeComment> likeDislikeComments;


    public User(String username, String email,String firstName, String lastName,Gender gender, double phoneNumber,String encode){
        this.username = username;
        this.email = email;
        this.firstName = firstName ;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber ;
        this.password = encode;


    }

//    public User(String username, String email, String encode) {
//
//    }
}
