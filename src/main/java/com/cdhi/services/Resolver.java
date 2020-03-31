package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.User;
import com.cdhi.domain.enums.Profile;
import com.cdhi.security.UserSS;
import com.cdhi.services.exceptions.AuthorizationException;

abstract class Resolver {
    protected static void isUserToAddAlreadyInBoard(User user, Board board) {
        if (user==null || board.getUsers().stream().anyMatch(u -> u.getId().equals(user.getId()))) {
            throw new AuthorizationException("Acesso negado, o usuário informado já participa do quadro.");
        }
    }

    protected static void isUserToRemoveNotInBoard(User user, Board board) {
        if (user==null || board.getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
            throw new AuthorizationException("Acesso negado, o usuário informado não participa do quadro.");
        }
    }

    protected static void isMyBoard(Board board) {
        UserSS user = UserService.authenticated();
        if (user==null || !user.hasRole(Profile.ADMIN) && !board.getOwner().getId().equals(user.getId())) {
            throw new AuthorizationException("Acesso negado, apenas o dono do quadro pode realizar a operação.");
        }
    }

    protected static void isMe(Integer userId) {
        UserSS user = UserService.authenticated();
        if (user==null || userId.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado, você não pode realizar a operação com o usuário logado.");
        }
    }

    protected static void isUserInBoard(Board board) {
        UserSS user = UserService.authenticated();
        if (user==null || !user.hasRole(Profile.ADMIN) && board.getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
            throw new AuthorizationException("Acesso negado, você não participa deste quadro.");
        }
    }

    protected static void amINotTheOwner(Board board, Integer userId) {
        if (board.getOwner().getId().equals(userId)) {
            throw new AuthorizationException("Acesso negado, você não pode sair de um quadro que é dono.");
        }
    }
}