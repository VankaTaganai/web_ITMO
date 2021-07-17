package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class TalkService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public long getTargetUserId(String id) throws ValidationException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong target user");
        }
    }

    public void validateMessage(long targetUserId, String text) throws ValidationException {
        if (text == null || text.length() > 1000) {
            throw new ValidationException("Message can't be longer than 1000 symbols");
        }
        if (userRepository.find(targetUserId) == null) {
            throw new ValidationException("Wrong target user");
        }
    }

    public void saveTalk(long sourceUserId, long targetUserId, String text) {
        talkRepository.save(new Talk(sourceUserId, targetUserId, text));
    }

    public List<Talk> findAllById(long id) {
        return talkRepository.findAllById(id);
    }
}
