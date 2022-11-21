package org.project.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.exception.TicketNotFoundException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "concert")
@Entity
@Getter
public class Concert extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 80)
  private String title;

  @Column(length = 120)
  private String description;

  @OneToMany(mappedBy = "concert")
  private List<Ticket> tickets;

  public Concert(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public List<Ticket> getAvailableTickets() {
    // TODO: 티켓이 많은 경우를 고려해서 우선은 휴리스틱하게 매직 넘버를 사용 -> 더 나은 방법 고려해보기
    return getTickets(50);
  }

  public List<Ticket> getAvailableTickets(Integer quantity) {
    List<Ticket> ret = getTickets(quantity);
    if (ret.size() < quantity) {
      throw new TicketNotFoundException(this);
    }
    return ret;
  }

  private List<Ticket> getTickets(Integer quantity) {
    List<Ticket> ret = tickets.stream()
        .filter(ticket -> ticket.getTicketOrder() == null)
        .limit(quantity)
        .collect(Collectors.toCollection(ArrayList::new));
    return ret;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Concert concert = (Concert) o;
    return id.equals(concert.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
