package com.emar.userregister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emar.userregister.adapter.UserRecyclerViewAdapter;
import com.emar.userregister.databinding.FragmentUserListBinding;
import com.emar.userregister.entities.User;
import com.emar.userregister.listener.UserListener;
import com.emar.userregister.repositories.UserRepository;
import com.emar.userregister.view_models.UserViewModel;

import java.util.List;

public class UserListFragment extends Fragment implements UserListener {

    private FragmentUserListBinding binding;

    private UserRecyclerViewAdapter adapter;

    private UserRepository userRepository;

    private UserViewModel userViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        userRepository = new UserRepository(getActivity().getApplication());
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        binding = FragmentUserListBinding.inflate(inflater, container, false);

        adapter = new UserRecyclerViewAdapter(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRepository.getUsersAll().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUserList(users);
            }
        });

        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(UserListFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onUserSelected(User user) {
        userViewModel.setSelectedUser(user);

        NavHostFragment.findNavController(UserListFragment.this)
                .navigate(R.id.action_FirstFragment_to_userProfileFragment);
    }
}