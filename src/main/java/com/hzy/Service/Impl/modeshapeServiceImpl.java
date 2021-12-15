package com.hzy.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzy.Controller.model.*;
import com.hzy.Service.modeshapeService;
import com.hzy.Utils.SerializeUtils;
import com.hzy.entity.Groups;
import com.hzy.mapper.GroupsMapper;
import com.hzy.mapper.userGroupMapper;
import com.hzy.security.SpringSecurityCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.acl.PrincipalImpl;

import javax.annotation.PostConstruct;
import javax.jcr.*;
import javax.jcr.security.AccessControlList;
import javax.jcr.security.AccessControlManager;
import javax.jcr.security.AccessControlPolicyIterator;
import javax.jcr.security.Privilege;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @Auther: hzy
 * @Date: 2021/8/6 20:59
 * @Description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class modeshapeServiceImpl implements modeshapeService {

    @Autowired
    private Repository repository;
    @Autowired
    private GroupsMapper groupsMapper;
    @Autowired
    private userGroupMapper userGroupMapper;
    @Autowired
    private userServiceImpl userService;

    //初始化项目的必要存储库
    @PostConstruct
    public void _init() {
        log.info("初始化项目 --> 文件库");
        try {
            Session session = repository.login();
            Node rootNode = session.getRootNode();

            if (!rootNode.hasNode("File_Repository")) {
                Node file_repository = rootNode.addNode("File_Repository");
                log.info("新增文献库 ==> {}", file_repository.getName());
                session.save();
            } else
                log.info("File_Repository已经存在！");

            if (!rootNode.hasNode("Collaboration_Repository")) {
                Node file_repository = rootNode.addNode("Collaboration_Repository");
                file_repository.addMixin("mode:accessControllable");
                log.info("新增团队总库 ==> {}", file_repository.getName());
                session.save();
            } else
                log.info("Collaboration_Repository已经存在！");

            session.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Session Login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Session session = null;
        try {
            session = repository.login(new SpringSecurityCredentials(authentication));
        } catch (RepositoryException e) {
            log.error("出现异常 ==> {}", e.getMessage());
        }
        return session;
    }


    @Override
    public Map<String, Object> getFileInfoAll() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<PropertiesModel> list = new ArrayList<>();
        Session session = Login();

        try {
            Node node = session.getNode("/File_Repository");
            NodeIterator iterator = node.getNodes();

            while (iterator.hasNext()) {

                Node itNode = iterator.nextNode();

                //不显示mode:acl的文件夹
                if (itNode.getName().contains("mode:acl"))
                    continue;

                String identifier = itNode.getIdentifier();
                Map<String, Object> objectMap = getFileInfoByID(identifier);
                list.add((PropertiesModel) objectMap.get("data"));
            }

            session.save();
            map.put("code", 200);
            map.put("data", list);
        } catch (RepositoryException e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("getFileInfoAll()-----------出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        session.logout();
        return map;
    }


    @Override
    public Map<String, Object> addFileInfo(PropertiesModel model) {

        Map<String, Object> map = new HashMap<>();
        Session session = Login();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        int x = 0;
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if ("admins".equals(authority.getAuthority())) {
                x = 1;
            }
        }

        if (x == 0) {
            map.put("code", 403);
            map.put("data", "无权限");
            return map;
        }

        try {

            Node node = session.getNode("/File_Repository");

            String node_name = UUID.randomUUID().toString().replace("-", "");
            log.info("UUID ---> node_name={}", node_name);

            //以UUID随机生成的字符串作为文件节点的名称
            Node fileName = node.addNode(node_name);

            //将文献的父节点的id也存进去
            model.setNodeIdentifier(fileName.getIdentifier());
            model.setScore(0);

            //将文献信息序列化成为字符串存储
            fileName.setProperty("obj", SerializeUtils.serializeToString(model));

            //构造存储节点信息的对象
            NodeModel nodemodel = new NodeModel();
            nodemodel.setNodeName(fileName.getName())
                    .setNodeIdentifier(fileName.getIdentifier())
                    .setNodePath(fileName.getPath());
            //存储节点Node的信息到  node_info属性中
            fileName.setProperty("node_info", SerializeUtils.serializeToString(nodemodel));

            //存储打分的信息
            HashMap<String, Double> hashMap = new HashMap<>();
            fileName.setProperty("scoreMap", SerializeUtils.serializeToString(hashMap));

            setPrivilege(session, fileName, "admins");
            setPrivilege(session, fileName, "Literature_library");

            session.save();

            map.put("code", 200);
            map.put("data", nodemodel);

        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("addFileInfo()------------出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        } finally {
            session.logout();
        }
        return map;
    }

    @Override
    public Map<String, Object> updateFileInfo(PropertiesModel model) {

        return null;
    }

    @Override
    public Map<String, Object> getFileInfoByID(String nodeIdentifier) {
        HashMap<String, Object> map = new HashMap<>();
        Session session = Login();

        try {
            Node nodeByIdentifier = session.getNodeByIdentifier(nodeIdentifier);

            Property obj = nodeByIdentifier.getProperty("obj");
            String s = String.valueOf(obj.getValue());
            //反序列化成对象
            PropertiesModel model = (PropertiesModel) SerializeUtils.deserializeToObject(s);

            session.save();
            map.put("code", 200);
            map.put("data", model);
        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("getFileInfoByID()------出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public Map<String, Object> deleteFileInfoByID(String nodeIdentifier) {
        HashMap<String, Object> map = new HashMap<>();
        Session session = Login();
        try {
            Node nodeByIdentifier = session.getNodeByIdentifier(nodeIdentifier);

            nodeByIdentifier.remove();

            session.save();
            map.put("code", 200);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("deleteFileInfoByID()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> setScore(String nodeIdentifier, double score) {
        HashMap<String, Object> map = new HashMap<>();
        Session session = Login();
        //获取当前用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            Node nodeByIdentifier = session.getNodeByIdentifier(nodeIdentifier);

            //获取存储评分的Map数据
            String scoreStr = String.valueOf(nodeByIdentifier.getProperty("scoreMap").getValue());

            //获取文献信息
            Property obj = nodeByIdentifier.getProperty("obj");
            String s = String.valueOf(obj.getValue());

            //反序列化为Map对象
            HashMap<String, Double> scoreMap = (HashMap<String, Double>) SerializeUtils.deserializeToObject(scoreStr);

            //反序列化成存储文献信息的对象
            PropertiesModel model = (PropertiesModel) SerializeUtils.deserializeToObject(s);

            //存入评分数据，如果该用户已经评分过，则将覆盖上一次的评分。
            scoreMap.put(authentication.getName(), score);
            nodeByIdentifier.setProperty("scoreMap", SerializeUtils.serializeToString(scoreMap));


            log.info("{} 为文献{} 评分=>{}", authentication.getName(), model.getName(), score);


            //求平均分存储到文献的信息对象中
            double count = 0;
            for (Double aDouble : scoreMap.values()) {
                count += aDouble;
            }
            count = count / scoreMap.size();
            model.setScore(count);
            nodeByIdentifier.setProperty("obj", SerializeUtils.serializeToString(model));


            log.info(model.toString());

            session.save();
            session.logout();
            map.put("code", 200);
            map.put("msg", "succeed");
        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> getAll(String nodeIdentifier) {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<NodeModel> list = new ArrayList<>();
        ArrayList<FileModel> list1 = new ArrayList<>();

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Session session = Login();
        Node node;
        try {
            if (nodeIdentifier != null && nodeIdentifier.length() > 0)
                node = session.getNodeByIdentifier(nodeIdentifier);
            else
                node = session.getRootNode().getNode(name + "_Repository");

            NodeIterator iterator = node.getNodes();

            while (iterator.hasNext()) {
                Node itNode = iterator.nextNode();

                //不显示mode:acl的文件夹
                if (itNode.getName().contains("mode:acl"))
                    continue;

                String s = String.valueOf(itNode.getProperty("node_info").getValue());
                //反序列化构建对象--> 获得节点的信息
                NodeModel node_info = (NodeModel) SerializeUtils.deserializeToObject(s);

                if (!node_info.getNodeName().contains("file"))
                    list.add(node_info);

                else {
                    //取文献引用的值
//                    String quote = String.valueOf(itNode.getProperty("Quote").getValue());
                    String file = node_info.getNodeName().replace("_file", "");
                    FileModel fileModel = new FileModel();

                    fileModel
                            .setNodeName(file)
                            .setNodeIdentifier(node_info.getNodeIdentifier());
                    list1.add(fileModel);
                }
            }

            map.put("code", 200);
            map.put("node", list);
            map.put("file", list1);

        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("getAll()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> addNode(String nodeIdentifier, String nodeName, int f) {
        HashMap<String, Object> map = new HashMap<>();

        Session session = Login();
        Node node;
        try {
            if (nodeIdentifier != null && nodeIdentifier.length() > 0)
                node = session.getNodeByIdentifier(nodeIdentifier);
            else {
                String name = SecurityContextHolder.getContext().getAuthentication().getName();
                node = session.getRootNode().getNode(name + "_Repository");
            }

            String uuid = UUID.randomUUID().toString().replace("-", "");
            Node addNode = node.addNode(uuid);
            NodeModel nodeModel = new NodeModel();

            nodeModel.setNodeName(nodeName)
                    .setNodeIdentifier(addNode.getIdentifier())
                    .setNodePath(addNode.getPath());
            //存储新建节点的信息
            addNode.setProperty("node_info", SerializeUtils.serializeToString(nodeModel));

            map.put("code", 200);
            map.put("data", nodeModel);

            if (f != 1) {
                //为节点添加权限
                String auth = SecurityContextHolder.getContext().getAuthentication().getName();

                setPrivilege(session, addNode, auth);
                setPrivilege(session, addNode, "ShareAll");
            }


            session.save();
            session.logout();
        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("getAll()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> remove(String nodeIdentifier) {
        HashMap<String, Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Session session = Login();
        try {
            Node nodeByIdentifier = session.getNodeByIdentifier(nodeIdentifier);

            nodeByIdentifier.remove();

            map.put("code", 200);

            session.save();
            session.logout();
        } catch (Exception e) {
            map.put("code", 403);
            map.put("msg", "无权限");
            map.put("error", e.getMessage());
            log.error("remove()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public Map<String, Object> getFileById(String nodeIdentifier) {
        HashMap<String, Object> map = new HashMap<>();

        Session session = Login();
        Node node;
        try {
            node = session.getNodeByIdentifier(nodeIdentifier);

            String quote = String.valueOf(node.getProperty("Quote").getValue());    //取得文献的引用值

            if (node.hasProperty("notes")) {
                // 如果有文献的笔记，取文献的笔记
                String notes = String.valueOf(node.getProperty("notes").getValue());
                map.put("notes", notes);
            } else
                map.put("notes", null);

            Map<String, Object> literature = getFileInfoByID(quote);    //取得文献的真实信息
            map.put("data", literature.get("data"));     //存map
            map.put("code", 200);

        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("getAll()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public Map<String, Object> addFile(String nodeIdentifier, String fileName, String fileUrl) {
        HashMap<String, Object> map = new HashMap<>();

        Session session = Login();
        Node node;
        try {
            Map<String, Object> map1 = addNode(nodeIdentifier, fileName + "_file", 1);

            NodeModel nodeModel = (NodeModel) map1.get("data");

            String id = nodeModel.getNodeIdentifier();
            node = session.getNodeByIdentifier(id);
            //关联的文献库id
            node.setProperty("Quote", fileUrl);

            //存储批注的信息
            HashMap<String, Object> AnnotationMap = new HashMap<>();
            node.setProperty("Annotation", SerializeUtils.serializeToString(AnnotationMap));

            session.save();

            session.logout();
            map.put("code", 200);
            map.put("data", new addFileModel(id, fileName, fileUrl));

        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("addFile()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public boolean setPrivilege(Session session, Node node, String GroupName) {
        try {
            node.addMixin("mode:accessControllable");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String path = node.getPath();
            String[] strings;

            if (GroupName.equals(auth.getName()) || GroupName.equals("admins")) {
                strings = new String[]{"13"};
            } else if (GroupName.equals("ShareAll")) {
                strings = new String[]{"0"};
            } else {
                Groups selectOne = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));
                if (selectOne == null)
                    throw new RuntimeException("该组不存在，请先创建该组");
                else if (!selectOne.getOwner().equals(auth.getName()))
                    throw new RuntimeException("无权限，你并非此组的组长");
                strings = selectOne.getAuthority().split(",");
            }

            AccessControlList acl;
            AccessControlManager acm = session.getAccessControlManager();

            String[] Privileges = new String[]{
                    Privilege.JCR_READ,
                    Privilege.JCR_MODIFY_PROPERTIES,
                    Privilege.JCR_ADD_CHILD_NODES,
                    Privilege.JCR_REMOVE_NODE,
                    Privilege.JCR_REMOVE_CHILD_NODES,
                    Privilege.JCR_WRITE,
                    Privilege.JCR_READ_ACCESS_CONTROL,
                    Privilege.JCR_MODIFY_ACCESS_CONTROL,
                    Privilege.JCR_LOCK_MANAGEMENT,
                    Privilege.JCR_VERSION_MANAGEMENT,
                    Privilege.JCR_NODE_TYPE_MANAGEMENT,
                    Privilege.JCR_RETENTION_MANAGEMENT,
                    Privilege.JCR_LIFECYCLE_MANAGEMENT,
                    Privilege.JCR_ALL
            };

            Privilege[] Permissions = new Privilege[strings.length];

            for (int i = 0; i < strings.length; i++) {
                Permissions[i] = acm.privilegeFromName(Privileges[Integer.parseInt(strings[i])]);
            }
            AccessControlPolicyIterator it = acm.getApplicablePolicies(path);
            if (it.hasNext()) {
                acl = (AccessControlList) it.nextAccessControlPolicy();
            } else {
                acl = (AccessControlList) acm.getPolicies(path)[0];
            }

            if (GroupName.equals(auth.getName())) {
                acl.addAccessControlEntry(new PrincipalImpl(auth.getName()), Permissions);
                acl.addAccessControlEntry(new PrincipalImpl("admins"), Permissions);
                acm.setPolicy(path, acl);
                session.save();
                return true;

            } else if (GroupName.equals("ShareAll")) {
                acl.addAccessControlEntry(new PrincipalImpl("ShareAll"), Permissions);
                acm.setPolicy(path, acl);
                session.save();
                return true;
            }
            acl.addAccessControlEntry(new PrincipalImpl(GroupName), Permissions);
            acm.setPolicy(path, acl);
            session.save();
        } catch (RepositoryException e) {
            log.error("setPrivilege()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> permissionsForNode(String nodeIdentifier, String groupName) {
        HashMap<String, Object> map = new HashMap<>();
        Session session = Login();
        try {

            Node nodeByIdentifier = session.getNodeByIdentifier(nodeIdentifier);

            setPrivilege(session, nodeByIdentifier, groupName);

            map.put("code", 200);
            map.put("msg", "succeed");
        } catch (RepositoryException e) {
            map.put("code", 403);
            map.put("msg", "无权限");
            map.put("error", e.getMessage());
            log.error("permissionsForNode()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> getAllTeam(String nodeIdentifier) {
        HashMap<String, Object> map = new HashMap<>();

        ArrayList<NodeModel> list = new ArrayList<>();
        ArrayList<FileModel> list1 = new ArrayList<>();

        Session session = Login();
        Node node;
        try {
            if (nodeIdentifier != null && nodeIdentifier.length() > 0)
                node = session.getNodeByIdentifier(nodeIdentifier);
            else
                node = session.getNode("/Collaboration_Repository");

            NodeIterator iterator = node.getNodes();

            while (iterator.hasNext()) {
                Node itNode = iterator.nextNode();

                //不显示mode:acl的文件夹
                if (itNode.getName().contains("mode:acl"))
                    continue;


                String s = String.valueOf(itNode.getProperty("node_info").getValue());
                //反序列化构建对象--> 获得节点的信息
                NodeModel node_info = (NodeModel) SerializeUtils.deserializeToObject(s);

                if (!node_info.getNodeName().contains("file"))
                    list.add(node_info);
                else {
                    //取文献引用的值
//                    String quote = String.valueOf(itNode.getProperty("Quote").getValue());
                    String file = node_info.getNodeName().replace("_file", "");
                    FileModel fileModel = new FileModel();

                    fileModel
                            .setNodeName(file)
                            .setNodeIdentifier(node_info.getNodeIdentifier());
                    list1.add(fileModel);
                }
            }

            map.put("code", 200);
            map.put("node", list);
            map.put("file", list1);

        } catch (Exception e) {

            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("getAll()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean create_Team_Node(String groupName, String nodeName) {
        Session session = Login();
        Node node;
        try {
            node = session.getNode("/Collaboration_Repository");

            String uuid = UUID.randomUUID().toString().replace("-", "");
            Node addNode = node.addNode(uuid);
            NodeModel nodeModel = new NodeModel();

            nodeModel.setNodeName(nodeName)
                    .setNodeIdentifier(addNode.getIdentifier())
                    .setNodePath(addNode.getPath());
            //存储新建节点的信息
            addNode.setProperty("node_info", SerializeUtils.serializeToString(nodeModel));

            //设置权限
            //自己有全部权限
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            setPrivilege(session, addNode, authentication.getName());

            if (groupName != null && groupName.length() > 0)
                setPrivilege(session, addNode, groupName);


            session.save();
            session.logout();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("create_Team_Node()-----出现异常 ==> {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, Object> create_Team(String groupName, String nodeName) {
        HashMap<String, Object> map = new HashMap<>();


        try {
            userService.createGroup(groupName, "0,5");

            boolean team_node = create_Team_Node(groupName, nodeName);
            if (!team_node)
                throw new RuntimeException("团队库初始化失败!");
            map.put("code", 200);
            map.put("msg", "团队已经成功完成创建");

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return map;
    }

    @Override
    public Map<String, Object> getGroupsOfNode(String nodeIdentifier) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();

        Session session = Login();

        try {
            Node node = session.getNodeByIdentifier(nodeIdentifier);
            Node aclName = node.getNode("mode:acl");
            NodeIterator iterator = aclName.getNodes();
            while (iterator.hasNext()) {
                Node itNode = iterator.nextNode();
                String name = itNode.getName();
                if (Stream.of("Literature_library", "ShareAll", "admins").noneMatch(name::equals))
                    list.add(name);
            }
            map.put("code", 200);
            map.put("data", list);
        } catch (RepositoryException e) {
            map.put("code", 403);
            map.put("msg", "获取异常");
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> delGroupForNode(String nodeIdentifier, String groupName) {
        Map<String, Object> map = new HashMap<>();
        Session session = Login();
        int f = 0;
        try {
            Node node = session.getNodeByIdentifier(nodeIdentifier);
            Node aclName = node.getNode("mode:acl");
            NodeIterator iterator = aclName.getNodes();
            while (iterator.hasNext()) {
                Node itNode = iterator.nextNode();
                if (itNode.getName().equals(groupName)) {
                    itNode.remove();
                    f = 1;
                }
            }
            session.save();
            map.put("code", 200);
            if (f == 1)
                map.put("msg", "撤销关联成功");
            else
                map.put("msg", "此库不受该团队管理");
        } catch (RepositoryException e) {
            map.put("code", 403);
            map.put("msg", "无权限");
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> removeCollaboration(String nodeIdentifier) {

        HashMap<String, Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Session session = Login();
        try {
            Node nodeByIdentifier = session.getNodeByIdentifier(nodeIdentifier);
            int x = -1;

            boolean b = nodeByIdentifier.hasNode("mode:acl");
            if (b) {
                x=0;
                Node node = nodeByIdentifier.getNode("mode:acl");
                NodeIterator iterator = node.getNodes();
                while (iterator.hasNext()) {
                    Node nextNode = iterator.nextNode();
                    if (nextNode.getName().equals(auth.getName())) {
                        x=1;
                        break;
                    }
                }
            }

            if (x == 0) {
                map.put("code", 403);
                map.put("data", "无权限");
                return map;
            }

            nodeByIdentifier.remove();

            map.put("code", 200);

            session.save();
            session.logout();
        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("removeCollaboration()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> addAnnotation(addAnnotationModel model) {
        HashMap<String, Object> map = new HashMap<>();

        String nodeIdentifier = model.getNodeIdentifier();
        String annotation = model.getAnnotation();
        if (nodeIdentifier.isEmpty()||annotation.isEmpty()){
            map.put("code",403);
            map.put("msg","参数缺失");
            return map;
        }

        Session session = Login();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            Node node = session.getNodeByIdentifier(model.getNodeIdentifier());
            Property annotation1 = node.getProperty("Annotation");

            String value = String.valueOf(annotation1.getValue());
            Map<String,Object> AnnotationMap = (Map<String, Object>) SerializeUtils.deserializeToObject(value);

            AnnotationMap.put(auth.getName(),model.getAnnotation());
            node.setProperty("Annotation", SerializeUtils.serializeToString(AnnotationMap));
            session.save();
            map.put("code", 200);
        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("addAnnotation()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public Map<String, Object> getAnnotations(String nodeIdentifier) {
        HashMap<String, Object> map = new HashMap<>();

        Session session = Login();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            Node node = session.getNodeByIdentifier(nodeIdentifier);
            Property annotation1 = node.getProperty("Annotation");

            String value = String.valueOf(annotation1.getValue());
            Map<String,Object> AnnotationMap = (Map<String, Object>) SerializeUtils.deserializeToObject(value);

            map.put("code", 200);
            map.put("data",AnnotationMap);
        } catch (Exception e) {
            map.put("code", 403);
            map.put("error", e.getMessage());
            log.error("addAnnotation()-----出现异常 ==> {}", e.getMessage());
            e.printStackTrace();
        }

        return map;
    }

}
