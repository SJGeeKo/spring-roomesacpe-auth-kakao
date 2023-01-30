package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.ui.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;
    public final MemberService memberService;

    public ReservationController(ReservationService reservationService, MemberService memberService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity createReservation(@AuthenticationPrincipal Member member,
                                            @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(reservationRequest.getScheduleId(), reservationRequest.getName());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        reservationService.deleteById(id, member.getUsername());

        return ResponseEntity.noContent().build();
    }
}
