from dataclasses import dataclass, field
from BookEntry import BookEntry


@dataclass
class BookCategory:
    key: str
    description: str
    icon: str = None
    name: str = None
    id: str = None
    entries: list[BookEntry] = field(default_factory=list)

    def __post_init__(self):
        if self.name is None:
            self.name = ' '.join(word.capitalize() for word in self.key.replace('_', ' ').split())
        if self.icon is None:
            self.icon = f"jaavaa:{self.key}"
        if self.id is None:
            self.id = f"jaavaa:{self.key}"

    def to_dict(self) -> dict:
        return {
            "key": self.key,
            "category": {
                "name": self.name,
                "icon": self.icon,
                "description": self.description
            },
            "entries": self.entries
        }
