class ItemRepository(private val itemDao: ItemDao) {

    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }

    suspend fun update(item: Item) {
        itemDao.update(item)
    }

    suspend fun delete(item: Item) {
        itemDao.delete(item)
    }

    suspend fun getAllItems(): List<Item> {
        return itemDao.getAllItems()
    }
}
