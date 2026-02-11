package com.inf.cscb869_pharmacy.controller;

import com.inf.cscb869_pharmacy.config.SecurityConfig;
import com.inf.cscb869_pharmacy.data.entity.Recipe;
import com.inf.cscb869_pharmacy.service.RecipeService;
import com.inf.cscb869_pharmacy.service.UserService;
import com.inf.cscb869_pharmacy.web.api.RecipeApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {RecipeApiController.class, SecurityConfig.class})
class RecipeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private UserService userService;

    @Test
    void getRecipesStatusOk() throws Exception {
        given(recipeService.getRecipes()).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/recipes")
                        .with(jwt()
                                .authorities(List.of(new SimpleGrantedAuthority("doctor")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "dr_ivanov"))
                        )
                )
                .andExpect(status().isOk());
    }

    @Test
    void getRecipesStatusUnauthorized() throws Exception {
        given(recipeService.getRecipes()).willReturn(null);
        mockMvc.perform(get("/api/recipes")
                        .with(anonymous()))
                .andExpect(status().isOk());
    }

    @Test
    void getRecipesStatusForbidden() throws Exception {
        given(recipeService.getRecipes()).willReturn(null);
        mockMvc
                .perform(get("/api/recipes")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("seller"))))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"doctor"})
    void getRecipesTest() throws Exception {
        Recipe recipe1 = Recipe.builder()
                .creationDate(LocalDate.of(2024,10,10))
                .build();
        Recipe recipe2 = Recipe.builder()
                .creationDate(LocalDate.of(2024,11,11))
                .build();
        List<Recipe> recipesExpected = Arrays.asList(recipe1, recipe2);

        given(recipeService.getRecipes()).willReturn(recipesExpected);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(recipe1.getId()), Long.class))
                .andExpect(jsonPath("$[0].creationDate", is(LocalDate.of(2024,10,10).toString())))
                .andExpect(jsonPath("$[1].id", is(recipe2.getId()), Long.class))
                .andExpect(jsonPath("$[1].creationDate".toString(), is(LocalDate.of(2024,11,11).toString())))
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = {"doctor"})
    void getRecipeByIdTest() throws Exception {
        Recipe recipe1 = Recipe.builder()
                .creationDate(LocalDate.of(2024,10,10))
                .build();
        int recipeId = 1;
        given(recipeService.getRecipe(recipeId)).willReturn(recipe1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/recipes/{recipeId}", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(recipe1.getId()), Long.class))
                .andExpect(jsonPath("$.creationDate", is(LocalDate.of(2024,10,10).toString())))
                .andDo(print());
    }
}