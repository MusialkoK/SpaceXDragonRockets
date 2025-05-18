package service;

import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import com.kmu.service.impl.RocketStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class RocketStatusServiceTest {
    @InjectMocks
    private RocketStatusService rocketStatusService;

    @Test
    void isStatusChangedToInSpace() {
        //given
        Rocket rocket = new Rocket("Dragon 1");

        //when
        boolean inSpace = rocketStatusService.changeStatusToInSpace(rocket);

        //then
        assertTrue(inSpace);
        assertEquals(RocketStatus.IN_SPACE, rocket.getStatus());
    }

}