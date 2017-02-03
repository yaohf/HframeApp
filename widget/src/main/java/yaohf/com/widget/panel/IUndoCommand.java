package yaohf.com.widget.panel;


public interface IUndoCommand
{
    public void undo();
    public void redo();
    public void historydo();
    public boolean canUndo();
    public boolean canRedo();
    public boolean canHistorydo();
    public void onDeleteFromUndoStack();
    public void onDeleteFromRedoStack();
	
	
}
