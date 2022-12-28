#include<stdio.h>
#include<stdlib.h>
#include<string.h>

//图书信息的链表及其函数
 
struct bookinfo			//图书信息 
{
	char name[20];		//书名 
	char isbn[20];		//ISBN 
	float price;		//价格 
	int num;			//数量
	char publish[20];	//出版社 
	char pdate[20]; 	//出版日期 
	char bookclass[20];	//图书类别 
}; 

//创建图书链表 
struct node
{
	struct bookinfo data;
	struct node* next;
 } ;
struct node* list=NULL;		//定义图书链表 
// 创建表头 
 struct node* createhead()
 {
 	struct node* headnode = (struct node*)malloc(sizeof(struct node));
 	headnode->next=NULL;
 	return headnode;
 }
 //创建结点：为插入做准备   
 struct node* createnode(struct bookinfo data)
 {
 	struct node* newnode=(struct node*)malloc(sizeof(struct node));
 	newnode->data=data;
 	newnode->next=NULL;
 	return newnode;
  } 

//插入：表头法插入
void insertnode(struct node* headnode,struct bookinfo data) 
{
	struct node* newnode=createnode(data);
	newnode->next=headnode->next;
	headnode->next=newnode;
}
//由指定位置删除 
void deletenode(struct node* headnode,char *bookname)
{
	struct node* posleftnode=headnode;
	struct node* posnode=headnode->next;
	while(posnode !=NULL && strcmp(posnode->data.name,bookname))
	{
		posleftnode=posnode;
		posnode=posleftnode->next;
	}		
	if(posnode==NULL)
		return ;
	else
	{
		printf("删除成功！\n");
		posleftnode->next=posnode->next;
		free(posnode);
		posnode=NULL;
	}
}
//查找指定位置的结点   按照名称查找 
struct node* search(struct node* headnode,char* filename)
{
	struct node* posnode=headnode->next;
	while(posnode!=NULL&& strcmp(posnode->data.name,filename))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//按照ISNBN号查找 
struct node* searchbyisbn(struct node* headnode,char* tempisbn)
{
	struct node* posnode=headnode->next;
	while(posnode!=NULL&&strcmp(posnode->data.isbn,tempisbn))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//按照出版社查找
void searchbypublish(struct node* headnode,char* publishname)
{
	struct node* posnode=headnode->next;
	int  i=0; 
	while(posnode!=NULL)
	{
		if(strcmp(posnode->data.publish,publishname)==0)
		{
			++i;
			printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",posnode->data.name,posnode->data.isbn,posnode->data.price,posnode->data.num,posnode->data.publish,posnode->data.pdate,posnode->data.bookclass);
		}
		posnode=posnode->next;
	}
	if(i==0)
	{
		printf("未查询到相关信息！\n");
	}
 } 
//打印链表
void printflist(struct node* headnode)
{
	struct node* pmove=headnode->next ;
	printf("书名\t\tISBN\t\t价格\t\t数量\t\t出版社\t\t\t出版日期\t\t类别\n");
	while(pmove!=NULL)
	{
		printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",pmove->data.name,pmove->data.isbn,pmove->data.price,pmove->data.num,pmove->data.publish,pmove->data.pdate,pmove->data.bookclass);
		pmove=pmove->next;
	}
}
//文件存、写操作
 void saveinfofile(const char *filename,struct node* headnode)
 {
 	FILE *fp=fopen(filename,"w");
 	struct node* pmove=headnode->next;
 	while(pmove!=NULL)
 	{
 		fprintf(fp,"%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",pmove->data.name,pmove->data.isbn,pmove->data.price,pmove->data.num,pmove->data.publish,pmove->data.pdate,pmove->data.bookclass);
 		pmove=pmove->next;
	 }
	 fclose(fp);
 }
 //文件读操作
 void readinfofromfile(const char *filename,struct node* headnode) 
 {
 	FILE *fp=fopen(filename,"r");
 	if(fp==NULL)
 	{
 		fp=fopen(filename,"w+");		//第一次打开 ，创建出一个文件 
	}
	 struct bookinfo tempdata;
	 while(fscanf(fp,"%s\t\t%s\t\t%f\t\t%d\t\t%s\t\t%s\t\t%s\n",tempdata.name,tempdata.isbn,&tempdata.price,&tempdata.num,tempdata.publish,tempdata.pdate,tempdata.bookclass)!=EOF)
	 {
	 	insertnode(list,tempdata);
	 }
 	fclose(fp);
 }
  //冒泡排序 
 void sortlist(struct node* headnode)
 {
 	for(struct node* p=headnode->next;p!=NULL;p=p->next)
 	{
 		for(struct node* q=headnode->next;q->next!=NULL;q=q->next)
 		{
 			if(q->data.price>q->next->data.price)
 			{
 				struct bookinfo tempdata=q->data;
 				q->data=q->next->data;
 				q->next->data=tempdata;
			 }
		 }
	 }
	 printflist(headnode);
 }


//用户链表及相关处理 
struct userinfo			//用户信息 
{
	char name[20];		//姓名 
	int tel;			//电话
	char loginname[20];	//用户名 
	char password[20]; 	//密码 
}; 
struct usernode
{
	struct userinfo data;
	struct usernode* next;
 } ;
struct usernode* ulist=NULL;		//定义用户信息链表 
// 创建表头 
 struct usernode* ucreatehead()
 {
 	struct usernode* headnode = (struct usernode*)malloc(sizeof(struct usernode));
 	headnode->next=NULL;
 	return headnode;
 }
 //创建结点：为插入做准备   
 struct usernode* createnode(struct userinfo data)
 {
 	struct usernode* newnode=(struct usernode*)malloc(sizeof(struct usernode));
 	newnode->data=data;
 	newnode->next=NULL;
 	return newnode;
  } 

//插入：表头法插入
void uinsertnode(struct usernode* headnode,struct userinfo data) 
{
	struct usernode* newnode=createnode(data);
	newnode->next=headnode->next;
	headnode->next=newnode;
}
//
//文件存、写操作
 void usaveinfofile(const char *filename,struct usernode* headnode)
 {
 	FILE *fp=fopen(filename,"w");
 	struct usernode* pmove=headnode->next;
 	while(pmove!=NULL)
 	{
 		fprintf(fp,"%s\t\t%d\t\t%s\t\t%s\n",pmove->data.name,pmove->data.tel,pmove->data.loginname,pmove->data.password);
 		pmove=pmove->next;
	 }
	 fclose(fp);
 }
 //文件读操作
 void ureadinfofromfile(const char *filename,struct usernode* headnode) 
 {
 	FILE *fp=fopen(filename,"r");
 	if(fp==NULL)
 	{
 		fp=fopen(filename,"w+");		//第一次打开 ，创建出一个文件 
	}
	 struct userinfo tempdata;
	 while(fscanf(fp,"%s\t\t%d\t\t%s\t\t%s\n",tempdata.name,&tempdata.tel,tempdata.loginname,tempdata.password)!=EOF)
	 {
	 	uinsertnode(ulist,tempdata);
	 }
 	fclose(fp);
 }

//查找指定位置的结点   按照名称查找 
struct usernode* usearch(struct usernode* headnode,char* filename)
{
	struct usernode* posnode=headnode->next;
	while(posnode!=NULL&& strcmp(posnode->data.name,filename))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//用户登录 
struct usernode* usearchbylogin(struct usernode* headnode,char* filename)
{
	struct usernode* posnode=headnode->next;
	while(posnode!=NULL&& strcmp(posnode->data.loginname,filename))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//打印链表
void uprintflist(struct usernode* headnode)
{
	struct usernode* pmove=headnode->next ;
	printf("姓名\t\t电话\t\t用户名\t\t密码\n");
	while(pmove!=NULL)
	{
		printf("%s\t\t%d\t\t%s\t\t%s\n",pmove->data.name,pmove->data.tel,pmove->data.loginname,pmove->data.password);
		pmove=pmove->next;
	}
}




//主登录系统 
void mainlogin()
{
	printf("----------哈理工图书管理系统--------------\n");
	printf("\t\t0、退出系统\n");
	printf("\t\t1、学生登录\n");
	printf("\t\t2、管理员登录\n");
	printf("------------------------------------------\n");
	printf("请输入：\n");
}


  
  

//管理员页面
void admmenu()
{
	printf("---------------管理员系统-----------------------\n");
	printf("\t\t图书管理系统\n");
	printf("\t\t0.退出系统\n");
	printf("\t\t1.登记书籍\n");
	printf("\t\t2.浏览书籍\n");
	printf("\t\t3.书籍价格排序\n");
	printf("\t\t4.删除书籍\n");
	printf("\t\t5.查找书籍\n");
	printf("\t\t6.修改图书信息\n");
	printf("\t\t7.查看用户信息\n");
	printf("-----------------------------------------\n");
	printf("请输如0~6：");
 } 
 //管理员操作
 void admope()
 {
 	int admopekey;
 	scanf("%d",&admopekey);
 	struct bookinfo tempbook;
 	struct node* result=NULL;
 	switch(admopekey)
	{
	 case 0:
	 			printf("退出系统\n");
				printf("[退出成功]");
				system("pause");
				exit(0);
				break;
	 case 1:
	 			printf("登记书籍\n"); 
				printf("输入书籍的信息:\n名称\tISBN\t价格\t数量\t出版社\t出版日期\t类别\n");
				scanf("%s%s%f%d%s%s%s",tempbook.name,tempbook.isbn,&tempbook.price,&tempbook.num,tempbook.publish,tempbook.pdate,tempbook.bookclass); 
				insertnode(list,tempbook); 
				saveinfofile("library.txt",list);
				break;
	 case 2:
	 			printf("浏览书籍\n"); 
				printflist(list);
				break;
	 case 3:	
	 			printf("书籍排序\n"); 
				sortlist(list);
				break;
	 case 4:
	 			printf("删除书籍\n"); 
				printf("请输入删除书籍的名称："); 
				scanf("%s",tempbook.name);
				deletenode(list,tempbook.name);
				saveinfofile("library.txt",list);
				break;
	 case 5:
	 			printf("查找书籍\n"); 
				printf("请选择要查询的方式：(1~3)\n");
				printf("\t\t1、按照书籍名称查询：\n");
				printf("\t\t2、按照ISBN号查询：\n");
	 			printf("\t\t3、按照出版社查询：\n");
	 			int tempnum;
	 			scanf("%d",&tempnum);
	 			switch(tempnum)
	 			{
	 				case 1:
					 		printf("请输入要查找书籍的名称：");
						   scanf("%s",tempbook.name); 
						   result=search(list,tempbook.name);
						   if(result==NULL)
							{
								printf("未找到相关信息！\n");	
							}
						    else
					 		{
							printf("书名\t\tISBN\t\t价格\t\t数量\t\t出版社\t\t出版日期\t\t类别\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							}
							break;
					case 2:
							printf("请输入要查找书籍的ISBN:");
							scanf("%s",tempbook.isbn);
							result=searchbyisbn(list,tempbook.isbn);
							if(result==NULL)
							{
								printf("未找到相关信息！\n");
							}
						    else
					 		{
							printf("书名\t\tISBN\t\t价格\t\t数量\t\t出版社\t\t出版日期\t\t类别\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							
							}
							break;
					case 3:
							printf("请输入要查找的出版社：");
							scanf("%s",tempbook.publish);
							searchbypublish(list,tempbook.publish);
							break;
				}
				break;
	 case 6:	
	 		printf("请输入要修改书籍的名称：");
			scanf("%s",tempbook.name); 
			result=search(list,tempbook.name);
			if(result==NULL)
					printf("未找到相关书籍！");
			else
			{
				deletenode(list,tempbook.name);
				printf("请重新输入书籍的信息:\n名称，ISBN，价格，数量，出版社，出版日期,类别\n");
				scanf("%s%s%f%d%s%s%s",tempbook.name,&tempbook.isbn,&tempbook.price,&tempbook.num,tempbook.publish,tempbook.pdate,tempbook.bookclass); 
				insertnode(list,tempbook); 
				printf("修改完成！"); 
				saveinfofile("library.txt",list);
				break;
			 } 
			break;
	case 7:
			printf("\t\t1、显示所有用户信息：\n");
			printf("\t\t2、查找特定用户：\n");
			int a;
			scanf("%d",&a);
			switch(a)
			{
				case 1:
					uprintflist(ulist);
					break;
				case 2:
					printf("请输入用户名：");
					 char nb[20];
					 scanf("%s",nb);
					 struct usernode* uresult=NULL;
					uresult=usearchbylogin(ulist,nb);
					if(uresult==NULL)
					{
						printf("未查找到该用户！\n");
						break;
					}
					else{
						printf("姓名\t\t电话\t\t用户名\t\t密码\n");
						printf("%s\t\t%d\t\t%s\t\t%s\n",uresult->data.name,uresult->data.tel,uresult->data.loginname,uresult->data.password);
						break;
					}
			 } 
			
			break;
	} 
  } 
//管理员登录
void admlogin()
{
	char admn[20]="123456789";
	char admp[20]="987654321";
	char adn[20],adp[20];
	printf("请输入用户名：\n");
	scanf("%s",adn);
	if(strcmp(adn,admn)!=0)
	{
		printf("用户名错误！\n");
	}
	else
	{
		printf("请输入密码：\n");
		scanf("%s",adp);
		if(strcmp(adp,"987654321")==0)
		{
			printf("登录成功！\n");
			while(1)
			{
				system("cls");
				admmenu();
				admope();
				system("pause");
				
			}
		}
		else
		{
			printf("密码错误！"); 
		}
	}
 }  
 
 
 
 
 //学生页面
void stumenu()
{
	printf("--------------学生借阅系统--------------\n");
	printf("\t\t0、退出系统\n");
	printf("\t\t1、浏览图书\n");
	printf("\t\t2、查询图书\n");
	printf("\t\t3、借阅图书\n");
	printf("\t\t4、归还图书\n");
	printf("\t\t5、图书价格排序\n");
	printf("----------------------------------------\n");
	printf("请输入：\n");
 }  
 //学生登录菜单 
void stuloginmenu()
{
	printf("----------学生系统--------------\n");
	printf("\t\t1、登录\n");
	printf("\t\t2、注册\n");
	printf("\t\t3、修改密码\n");
	printf("--------------------------------\n");
	printf("请输入：\n");
} 

//借阅时间计算 
void time(int month1,int day1,int month2,int day2)
{
	
	if(month1==month2)
	{
		printf("共借阅%d天\n",day2-day1);
	}
	if(month1<month2)
	{
		printf("共借阅%d天\n",day2+30-day1);
	}
}

  



 
//学生操作
 void stuope()
 {
 	int stuopekey;
 	scanf("%d",&stuopekey);
 	struct bookinfo tempbook;
 	struct node* result=NULL;
 	switch(stuopekey)
 	{
 		case 0:
 			printf("退出系统\n");
			printf("[退出成功]");
			system("pause");
			exit(0);
 			break;
 		case 1:
 			printf("浏览书籍\n"); 
			printflist(list);
 			break;
 		case 2:
 				printf("查找书籍\n"); 
				printf("请选择要查询的方式：(1~3)\n");
				printf("\t\t1、按照书籍名称查询：\n");
				printf("\t\t2、按照ISBN号查询：\n");
	 			printf("\t\t3、按照出版社查询：\n");
	 			int tempnum;
	 			scanf("%d",&tempnum);
	 			switch(tempnum)
	 			{
	 				case 1:
					 		printf("请输入要查找书籍的名称：");
						   scanf("%s",tempbook.name); 
						   result=search(list,tempbook.name);
						   if(result==NULL)
							{
								printf("未找到相关信息！\n");	
							}
						    else
					 		{
							printf("书名\t\tISBN\t\t价格\t\t数量\t\t出版社\t\t\t出版日期\t\t类别\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							}
							break;
					case 2:
							printf("请输入要查找书籍的ISBN:");
							scanf("%s",tempbook.isbn);
							result=searchbyisbn(list,tempbook.isbn);
							if(result==NULL)
							{
								printf("未找到相关信息！\n");
							}
						    else
					 		{
							printf("书名\t\tISBN\t\t价格\t\t数量\t\t出版社\t\t\t出版日期\t\t类别\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							
							}
							break;
					case 3:
							printf("请输入要查找的出版社：");
							scanf("%s",tempbook.publish);
							searchbypublish(list,tempbook.publish);
							break;
				}
 			break;
 		case 3:
 				printf("借阅书籍\n"); 	//书籍存在，书籍数量-1     不存在，借阅失败 
				printf("请输入借阅的书名：");
				scanf("%s",tempbook.name); 
				result=search(list,tempbook.name);
				if(result==NULL)
				{
					printf("没有相关书籍！");
				}
				else
				{
					if(result->data.name>0)
					{
						result->data.num--;
						saveinfofile("library.txt",list);
						printf("借阅成功！请于一个月之内归还！"); 
					}
					else
					{
						printf("当前书籍无库存，借阅失败！"); 
					}
				}
 			break;
 		case 4:
 				printf("归还书籍\n"); //书籍+1 
				printf("请输入归还的书籍名：");
				scanf("%s",tempbook.name); 
				result=search(list,tempbook.name);
				if(result==NULL)
				{
					printf("该书不属于本图书馆！");
				}
				else
				{	int month1,month2,day1,day2;
					printf("请输入借书时的时间：\n某月份\t某天\n");
					scanf("%d%d",&month1,&day1);
					printf("请输入当前时间：\n某月份\t某天\n");
					scanf("%d%d",&month2,&day2); 
					result->data.num++;
					saveinfofile("library.txt",list);
					printf("书籍归还成功！");
					time(month1,day1,month2,day2); 
				 } 
 				break;
 		case 5:
 			printf("书籍排序\n"); 
			sortlist(list);
 			break;
	 }
  } 
  //学生登录 
void stulogin()
{
	char n[20],p[20];
	printf("\t\t请输入用户名：\n");
	scanf("%s",n);
	printf("\t\t请输入密码：\n");
	scanf("%s",p);
	struct usernode* result=NULL;
	result=usearchbylogin(ulist,n);
	if(result==NULL)
	{
		printf("该用户名未注册！\n");
	}
	else
	{
		if(strcmp(p,result->data.password)==0)
		{
			printf("登录成功！\n");
				while(1)
		{
			system("cls");
			stumenu();
			stuope();
			system("pause");		//暂停一下 ，防止闪屏 
			system("cls");			//清屏 
		}
		}
		else
		{
			printf("密码错误！\n");
		 } 
	}
 } 
  //学生选择 
void stuchose()
{
	int stukey;
	scanf("%d",&stukey);
	switch(stukey)
	{
		case 1:
			printf("【登录】\n");
			while(1)
			{
				system("cls");
				stulogin();
				system("pause");
				system("cls");
			}
			break;
		case 2:
			printf("[注册]\n");
			struct userinfo tempinfo;
			printf("请输入姓名、电话、用户名、密码：\n");
			scanf("%s%d%s%s",tempinfo.name,&tempinfo.tel,tempinfo.loginname,tempinfo.password);
			uinsertnode(ulist,tempinfo); 
			usaveinfofile("用户信息.txt",ulist);
			break;
		case 3:
			char n[20],p[20],m[20];
			printf("\t\t请输入用户名：\n");
			scanf("%s",n);
			printf("\t\t请输入密码：\n");
			scanf("%s",p);
			struct usernode* result=NULL;
			result=usearchbylogin(ulist,n);
			if(result==NULL)
			{
				printf("该用户名未注册！\n");
				break;
			}
			else{
				if(strcmp(p,result->data.password)==0)
				{
					printf("请重新输入密码:\n");
					scanf("%s",result->data.password);
					usaveinfofile("用户信息.txt",ulist);
					printf("修改成功！\n"); 
					break;
				 } 
				 else
				 {
				 	printf("密码错误！");
				 	break;
				 }
				
			}
	}
}





//汇总操作 
void keydown()
{
	mainlogin();
	int key;
	scanf("%d",&key);
	switch(key)
	{
		case 0:
			printf("退出系统\n");
			printf("[退出成功]");
			system("pause");
			exit(0);
 			break;
		case 1://学生登录 
			while(1)
			{
				stuloginmenu();
				stuchose();
			}
		case 2://管理员登录 
			 while(1)
			 {
			 	system("cls");
			 	admlogin();
			 }
	 } 
}
//主函数 
int main()
{
	list=createhead();
	readinfofromfile("library.txt",list);
	ulist=ucreatehead();
	ureadinfofromfile("用户信息.txt",ulist);
	keydown();
	return 0;
}
 

