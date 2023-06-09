package ch.resrc.gamerm.dataextractiontwitch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"server.servlet.context-path=/api"})
class ScraperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmailAddressWhenValidTwitchIdIsProvided() throws Exception {
        String twitchId = "monadart";

        this.mockMvc.perform(
                        get("/twitch/{twitchId}", twitchId)
                                .header("X-API-KEY", "12ce5076-facd-11ed-be56-0242ac120002")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("monadart87@gmail.com")));
    }

}
