package com.splitwise.controllers;

import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.exceptions.notfound.GroupNotFoundException;
import com.splitwise.exceptions.notfound.UserNotFoundException;
import com.splitwise.exceptions.validations.CannotModifyGroupException;
import com.splitwise.models.Group;
import com.splitwise.models.User;
import com.splitwise.repositories.interfaces.GroupRepository;
import com.splitwise.repositories.interfaces.UserRepository;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.settle.SettleGroupStrategy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupController {

    GroupRepository groupRepository;
    UserRepository userRepository;
    SettleGroupStrategy settleGroupStrategy;

    public GroupController(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public void setSettleGroupStrategy(SettleGroupStrategy settleGroupStrategy){
        this.settleGroupStrategy = settleGroupStrategy;
    }
    public Group createGroup(AuthenticationContext authenticationContext, List<Long> participantIds,String name){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        Set<User>  members = participantIds.stream()
                .map(id->userRepository.findById(id)
                        .orElseThrow(()-> new UserNotFoundException(""))).collect(Collectors.toSet());
         Group group = new Group(name,user,members);
        groupRepository.create(group);
        return group;
    }
    public Group updateGroup(AuthenticationContext authenticationContext,Long groupId,List<Long> participantIds,String name){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        Group  group = groupRepository.findById(groupId).orElseThrow(()->new GroupNotFoundException(""));
        if(!group.getAdmin().equals(user)){
            throw new CannotModifyGroupException("");
        }
        Set<User>  members = participantIds.stream()
                .map(id->userRepository.findById(id)
                        .orElseThrow(()-> new UserNotFoundException(""))).collect(Collectors.toSet());
        group.setMembers(members);
        group.setName(name);
        groupRepository.save(group);
        return group;
    }
    public Group deleteGroup(AuthenticationContext authenticationContext,Long groupId){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        Group  group = groupRepository.findById(groupId).orElseThrow(()->new GroupNotFoundException(""));
        if(!group.getAdmin().equals(user)){
            throw new CannotModifyGroupException("");
        }
        groupRepository.delete(groupId);
        return group;
    }

    public String settleUp(AuthenticationContext authenticationContext,Long groupId){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        Group group = groupRepository.findById(groupId).orElseThrow(()->new GroupNotFoundException("No group found"));

        if(!group.getMembers().contains(user)){
            throw new GroupNotFoundException("No group");
        }
        return settleGroupStrategy.settle(group);
    }
}
