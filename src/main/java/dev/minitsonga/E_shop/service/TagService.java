package dev.minitsonga.E_shop.service;

import dev.minitsonga.E_shop.model.Tag;
import dev.minitsonga.E_shop.repo.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    public Tag createTag(String name) {
        if (tagRepo.existsById(name))
            throw new IllegalArgumentException("Tag already exists ! : " + name);
        return tagRepo.save(new Tag(name));
    }

    public void deleteTag(String name) {
        if (!tagRepo.existsById(name))
            throw new RuntimeException("Tag doesn't exists ! : " + name);
        tagRepo.deleteById(name);
    }
}
