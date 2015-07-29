package my.playground.office.services;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClockImpl implements Clock {
    @Override
    public Instant getTime() {
        return Instant.now();
    }
}
