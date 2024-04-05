from dataclasses import dataclass, field


@dataclass
class BookEntry:
    key: str
    category: str
    name: str = None
    icon: str = None
    pages: list[dict] = field(default_factory=list)

    def __post_init__(self):
        if self.name is None:
            self.name = ' '.join(word.capitalize() for word in self.key.replace('_', ' ').split())
        if self.icon is None:
            self.icon = f"jaavaa:{self.key}"

    def to_dict(self) -> dict:
        return {
            "key": self.key,
            "entry": {
                "name": self.name,
                "icon": self.icon,
                "category": self.category,
                "pages": self.pages
            }
        }
