package com.inf.cscb869_pharmacy.service.impl;


import com.inf.cscb869_pharmacy.data.entity.Recipe;
import com.inf.cscb869_pharmacy.data.repo.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class RecipeServiceImplTest {
    @MockBean
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeServiceImpl recipeService;

    private Recipe recipe;

    @BeforeEach
    public void init() {
        recipe = Recipe.builder()
                .creationDate(LocalDate.of(2024,10,10))
                .build();
    }

    @Test
    @WithMockUser(authorities = {"doctor", "seller"})
    void getRecipeById() {
        given(recipeRepository.findById(1L))
                .willReturn(Optional.ofNullable(recipe));

        Recipe recipe1 =
                recipeService.getRecipe(1L);

        assertThat(recipe1).isNotNull();
    }

    @Test
    void getRecipesWithoutUser() throws Exception {
        assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> this.recipeService.getRecipes());
    }

    @Test
    @WithAnonymousUser
    void getRecipesWithAnonymousUser() throws Exception {
        assertThrows(AccessDeniedException.class,
                () -> this.recipeService.getRecipes());
    }

    @Test
    @WithMockUser(authorities = {"seller"})
    void getRecipesWithSellerUser() throws Exception {
        assertThrows(AccessDeniedException.class, () ->
                this.recipeService.getRecipes());
    }

    @Test
    @WithMockUser(authorities = {"doctor"})
    void getRecipesStatusOk() throws Exception {
        Recipe recipe1 = Recipe.builder()
                .creationDate(LocalDate.of(2024,10,10))
                .build();
        Recipe recipe2 = Recipe.builder()
                .creationDate(LocalDate.of(2024,10,10))
                .build();
        List<Recipe> recipesExpected = Arrays.asList(recipe1, recipe2);

        given(recipeRepository.findAll()).willReturn(recipesExpected);

        List<Recipe> recipesActual = recipeService.getRecipes();
        assertIterableEquals(recipesExpected, recipesActual);
    }
}