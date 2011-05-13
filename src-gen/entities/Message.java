package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "swag_message")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	public Message() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@ManyToOne()
	private User fromUser;

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	@ManyToOne()
	private User toUser;

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getToUser() {
		return toUser;
	}

	private String title;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	private java.util.Date date;

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public java.util.Date getDate() {
		return date;
	}

}
