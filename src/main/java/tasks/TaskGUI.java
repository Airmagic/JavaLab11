package tasks;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class TaskGUI extends JFrame{
    private JPanel mainPanel;
    private JTextField newTaskText;
    private JList<Task> taskList;
    private JButton addButton;
    private JCheckBox urgentCheckBox;
    
    private DefaultListModel<Task> listModel;
    
    private int rightClickTaskIndex;  // To keep track of which list item was right-clicked on.
    
    TaskGUI() {
        
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewTask();
            }
        });
        
        configureList();  // Bunch of JList setup

        getAllTasks();  // And get initial list of tasks
        
    }
    
    private void configureList() {
    
        // When a item is clicked on, toggle between complete and not complete.
        taskList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updateTaskIsComplete();
            }
        });
        
        // The model contains a list of Task objects to be displayed in the list
        listModel = new DefaultListModel<>();
        taskList.setModel(listModel);
    
        // Only want to select one item at a time.
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        // Create pop-up menu, with one menu item.
        JPopupMenu rightClickMenu = new JPopupMenu();
        JMenuItem deleteTask = new JMenuItem("Delete");
        
        // Add the action listener for clicking on the Delete menu option.
        deleteTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskList.setSelectedIndex(e.getID());
                deleteTask();
            }
        });
    
        rightClickMenu.add(deleteTask);
        
        taskList.setComponentPopupMenu(rightClickMenu);
    
        // A lot of code to remember which item the user right-clicked on
        taskList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rightClickTaskIndex = taskList.locationToIndex(e.getPoint());
                //rightClickTaskIndex =
            }
        
            @Override
            public void mousePressed(MouseEvent e) {
            
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
            
            }
        
            @Override
            public void mouseEntered(MouseEvent e) {
            
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
            
            }
        });
    }
    
    
    // Used to disable the task list while updates are in progress.
    private void enableGUI(boolean enabled) {
        taskList.setEnabled(enabled);
    }
    
    
    private void getAllTasks() {
        enableGUI(false);
        TaskClient.getAllTasks(this);
    }
    
    
    private void addNewTask() {
        enableGUI(false);
        Task task = new Task(newTaskText.getText(), urgentCheckBox.isSelected());  //todo validation
        newTaskText.setText("");
        urgentCheckBox.setSelected(false);
        TaskClient.addTask(this, task);
    }
    
    
    private void updateTaskIsComplete() {
        enableGUI(false);
        Task task = taskList.getSelectedValue();
        if (task != null) {
            task.setCompleted(!task.isCompleted());
            TaskClient.updateTask(this, task);
        }
    
    }
    
    private void deleteTask() {
        enableGUI(false);
        try {
            Task task = listModel.elementAt(rightClickTaskIndex);
            if (task != null)  { TaskClient.deleteTask(this, task); }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    protected void tasksUpdated() {
        getAllTasks();
    }
    
    
    protected void newTaskList(Task[] tasks) {
    
        System.out.println(tasks.length + " NEW TASKS");
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel = new DefaultListModel<>();
                for (Task t : tasks) {
                    listModel.addElement(t);
                }
                taskList.setModel(listModel);
    
                enableGUI(true);
            }
        });
    }
    
    
    public void taskError(Exception e) {
        System.err.println(e);
        enableGUI(true);
    }
    
}


