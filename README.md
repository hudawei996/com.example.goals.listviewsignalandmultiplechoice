# com.example.goals.listviewsignalandmultiplechoice

## android listview 单选和多选
## 样式并不是问题，不管你使用checkableLinearLayout，还是其他的item布局。
## 都是可以在item布局中，控制那个显示被选中控件的显示状态。
### 关键点：
### 1，选中数据集合
### 2，选中item样式的点击事件：
#### 2.1修改该item的样式，
#### 2.2修改选中数据集合
### 3，在getView()中:
#### 3.1给循环使用的convertView，设置位置标记即setTag(position)
#### 3.2在return convertView；前给对应位置设置数据集合中应该显示的状态


