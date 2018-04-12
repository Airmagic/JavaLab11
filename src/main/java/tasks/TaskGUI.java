package tasks;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by clara on 4/12/18.
 */
public class TaskGUI extends JFrame{
    private JPanel mainPanel;
    private JTextField newTaskText;
    private JList<Task> taskList;
    private JButton addButton;
    private JCheckBox urgentCheckBox;
    
    TaskGUI() {
        
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewTask();
            }
        });
        
        taskList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updateTaskComplete();
            }
        });
        
        
        JPopupMenu rightClickMenu = new JPopupMenu();
        JMenuItem deleteTask = new JMenuItem("Delete");
        deleteTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskList.setSelectedIndex(e.getID());
                deleteTask();
            }
        });

        rightClickMenu.add(deleteTask);
        
        taskList.setComponentPopupMenu(rightClickMenu);
        
        getAllTasks();
        
    }
    
    
    private void getAllTasks() {
        TaskClient.getAllTasks(this);
    }
    
    
    private void addNewTask() {
        Task task = new Task(newTaskText.getText(), urgentCheckBox.isSelected());  //todo validation
        newTaskText.setText("");
        urgentCheckBox.setSelected(false);
        TaskClient.addTask(task);
        getAllTasks();
    }
    
    private void updateTaskComplete() {
        Task selected = taskList.getSelectedValue();
        TaskClient.updateTask(selected);
        getAllTasks();
    }
    
    private void deleteTask() {
        Task task = taskList.getSelectedValue();
        TaskClient.deleteTask(task);
    }
    
    
    protected void tasksUpdated(Task[] tasks) {
        taskList.setListData(tasks);
    }
    
    
    
}
