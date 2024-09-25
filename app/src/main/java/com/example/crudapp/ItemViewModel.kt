import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    fun insert(item: Item) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun update(item: Item) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun delete(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun getAllItems(callback: (List<Item>) -> Unit) {
        viewModelScope.launch {
            val items = repository.getAllItems()
            callback(items)
        }
    }
}
